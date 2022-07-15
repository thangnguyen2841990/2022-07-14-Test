package com.codegym.thang.service.user;

import com.codegym.thang.model.entity.Category;
import com.codegym.thang.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends UserDetailsService {
    Page<User> findAll(Pageable pageable);
    Optional<User> findById(Long id);
    User save(User user);
    void remove(Long id);
    User saveShop(User user);
    User saveAdmin(User user);
    User saveUser(User user);

    User findByUsername(String username);

}
