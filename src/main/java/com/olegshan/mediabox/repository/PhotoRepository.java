package com.olegshan.mediabox.repository;

import com.olegshan.mediabox.model.Photo;
import com.olegshan.mediabox.model.UserClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Bors on 22.05.2016.
 */
public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    List<Photo> findByUserClient(UserClient userClient, Pageable pageable);

    List<Photo> findByUserClientAndNameLikeOrUserClientAndTagsLikeAllIgnoreCase(UserClient userClient, String name, UserClient userClient2, String tags);

}
