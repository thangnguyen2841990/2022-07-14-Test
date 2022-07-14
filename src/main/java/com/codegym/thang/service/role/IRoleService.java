package com.codegym.thang.service.role;

import com.codegym.thang.model.entity.Category;
import com.codegym.thang.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRoleService {
    Page<Role> findAll(Pageable pageable);
    Optional<Role> findById(Long id);
    Role save(Role role);
    void remove(Long id);
}
