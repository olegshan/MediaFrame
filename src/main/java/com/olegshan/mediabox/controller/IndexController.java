package com.olegshan.mediabox.controller;

import com.olegshan.mediabox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by Bors on 12.05.2016.
 */
@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index(Model model, Principal principal) {
        if (principal != null) {
            String name = principal.getName();
            model.addAttribute("user", userService.findOneWithPhotos(name));
        }
        return "index";
    }

}
