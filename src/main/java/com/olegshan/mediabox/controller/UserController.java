package com.olegshan.mediabox.controller;

import com.olegshan.mediabox.model.UserClient;
import com.olegshan.mediabox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Bors on 22.05.2016.
 */

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ModelAttribute("user")
    public UserClient construct() {
        return new UserClient();
    }

    @RequestMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @RequestMapping("/users/{id}")
    public String detail(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.findOneWithPhotos(id));
        return "user-detail";
    }

    @RequestMapping("/register")
    public String showRegister() {
        return "user-register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegister(@ModelAttribute("user") UserClient userClient, RedirectAttributes redirectAttributes) {
        userService.save(userClient);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/register";
    }
}
