package com.olegshan.mediabox.service;

import com.olegshan.mediabox.model.Photo;
import com.olegshan.mediabox.model.Role;
import com.olegshan.mediabox.model.UserClient;
import com.olegshan.mediabox.repository.PhotoRepository;
import com.olegshan.mediabox.repository.RoleRepository;
import com.olegshan.mediabox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 22.05.2016.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserClient> findAll() {
        return userRepository.findAll();
    }

    public UserClient findOne(int id) {
        return userRepository.findOne(id);
    }

    @Transactional
    public UserClient findOneWithPhotos(int id) {
        UserClient userClient = userRepository.findOne(id);
        List<Photo> photos = photoRepository.findByUserClient(userClient,
                new PageRequest(0, 10, Sort.Direction.DESC, "publishedDate"));
        userClient.setPhotos(photos);
        return userClient;
    }

    public void save(UserClient userClient) {
        userClient.setEnabled(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userClient.setPassword(encoder.encode(userClient.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        userClient.setRoles(roles);
        userRepository.save(userClient);
    }

    public UserClient findOneWithPhotos(String name) {
        UserClient userClient = userRepository.findByName(name);
        return findOneWithPhotos(userClient.getId());
    }

    public void delete(int id) {
        userRepository.delete(id);
    }

    public UserClient findOne(String username) {
        return userRepository.findByName(username);
    }
}
