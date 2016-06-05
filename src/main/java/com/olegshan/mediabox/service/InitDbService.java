package com.olegshan.mediabox.service;

import com.olegshan.mediabox.model.Photo;
import com.olegshan.mediabox.model.Role;
import com.olegshan.mediabox.model.UserClient;
import com.olegshan.mediabox.repository.PhotoRepository;
import com.olegshan.mediabox.repository.RoleRepository;
import com.olegshan.mediabox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Bors on 22.05.2016.
 */
@Transactional
@Service
public class InitDbService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @PostConstruct
    public void init() {
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleRepository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleRepository.save(roleAdmin);

        UserClient userAdmin = new UserClient();
        userAdmin.setEnabled(true);
        userAdmin.setName("admin");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userAdmin.setPassword(encoder.encode("admin"));
        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);
        roles.add(roleUser);
        userAdmin.setRoles(roles);
        userRepository.save(userAdmin);

        /*Photo testPhoto1 = new Photo();
        testPhoto1.setTags("cat kitty kitten one");
        testPhoto1.setName("Cute kitten");
        testPhoto1.setSource("Reuters");
        testPhoto1.setLocation("http://s3.amazonaws.com/assets.prod.vetstreet.com/2a/cd/ee484be546418f40cc3cbc194b52/kitten-in-arms-thinkstockphotos-106397271-335lc070915jpg.jpg");
        testPhoto1.setThumbnailPath("http://s3.amazonaws.com/assets.prod.vetstreet.com/2a/cd/ee484be546418f40cc3cbc194b52/kitten-in-arms-thinkstockphotos-106397271-335lc070915jpg.jpg");
        testPhoto1.setPublishedDate(new Date());
        testPhoto1.setUserClient(userAdmin);
        photoRepository.save(testPhoto1);

        Photo testPhoto2 = new Photo();
        testPhoto2.setTags("cat kitty kitten two");
        testPhoto2.setName("Cute kitten");
        testPhoto2.setSource("Getty images");
        testPhoto2.setLocation("http://www.expo-aristogatti.com/wp-content/uploads/2014/07/cute-kitty.png");
        testPhoto2.setThumbnailPath("http://www.expo-aristogatti.com/wp-content/uploads/2014/07/cute-kitty.png");
        testPhoto2.setPublishedDate(new Date());
        testPhoto2.setUserClient(userAdmin);
        photoRepository.save(testPhoto2);*/
    }

}
