package com.olegshan.mediabox.repository;

import com.olegshan.mediabox.model.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Bors on 22.05.2016.
 */
public interface UserRepository extends JpaRepository<UserClient, Integer> {
    UserClient findByName(String name);
}
