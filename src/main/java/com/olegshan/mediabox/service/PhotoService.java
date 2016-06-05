package com.olegshan.mediabox.service;

import com.olegshan.mediabox.model.Photo;
import com.olegshan.mediabox.model.UserClient;
import com.olegshan.mediabox.repository.PhotoRepository;
import com.olegshan.mediabox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 28.05.2016.
 */
@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    public void save(Photo photo, String name) {
        UserClient userClient = userRepository.findByName(name);
        photo.setUserClient(userClient);
        photoRepository.save(photo);
    }

    public void update(Photo photo) {
        photoRepository.save(photo);
    }

    public Photo findOne(int id) {
        return photoRepository.findOne(id);
    }

    // It's far not brilliant. Needs refactoring
    public List<Photo> search(String query, String name) {
        List<Photo> list = new ArrayList<>();
        UserClient userClient = userRepository.findByName(name);
        list.addAll(photoRepository.findByUserClientAndNameLikeOrUserClientAndTagsLikeAllIgnoreCase(
                userClient, '%' + query + '%', userClient, '%' + query + '%'));
        return list;
    }

    @PreAuthorize("#photo.userClient.name == authentication.name")
    public void delete(@P("photo") Photo photo) {
        photoRepository.delete(photo);
    }
}
