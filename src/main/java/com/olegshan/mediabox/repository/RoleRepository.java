package com.olegshan.mediabox.repository;

import com.olegshan.mediabox.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Bors on 22.05.2016.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
