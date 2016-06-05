package com.olegshan.mediabox.controller;

import com.olegshan.mediabox.model.Photo;
import com.olegshan.mediabox.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by Bors on 02.06.2016.
 */
@Controller
public class SearchController {

    @Autowired
    PhotoService photoService;

    @RequestMapping("/search")
    @ModelAttribute("searchResult")
    public List<Photo> searchPics(@RequestParam("searchQuery") String query, Principal principal) {
        return photoService.search(query, principal.getName());
    }

}
