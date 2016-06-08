package com.olegshan.mediabox.controller;

import com.olegshan.mediabox.model.UserClient;
import com.olegshan.mediabox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String doRegister(@Valid @ModelAttribute("user") UserClient userClient, BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user-register";
        }
        userService.save(userClient);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/register";
    }

    @RequestMapping("/users/remove/{id}")
    public String removeUser(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @RequestMapping("/register/available")
    @ResponseBody
    public String available(@RequestParam String username) {
        Boolean available = userService.findOne(username) == null;
        return available.toString();
    }
}
