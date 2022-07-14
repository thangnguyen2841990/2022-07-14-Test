package com.codegym.thang.controller;

import com.codegym.thang.model.entity.Product;
import com.codegym.thang.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductRestController {
    @Autowired
    private IProductService productService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Product>> findAll(@PathVariable Long userId, @PageableDefault(value = 10) Pageable pageable) {
        Page<Product> products = this.productService.findAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product newProduct = this.productService.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> update(@PathVariable Long productId, @RequestBody Product product) {
        Optional<Product> productOptional = this.productService.findById(productId);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product newProduct = productOptional.get();
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setDescription(product.getDescription());
        newProduct.setQuantity(product.getQuantity());
        newProduct.setImages(product.getImages());

        this.productService.save(newProduct);

        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @DeleteMapping("/productId/{productId}")
    public ResponseEntity<Product> delete(@PathVariable Long productId) {
        Optional<Product> productOptional = this.productService.findById(productId);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.productService.remove(productId);
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }
}
