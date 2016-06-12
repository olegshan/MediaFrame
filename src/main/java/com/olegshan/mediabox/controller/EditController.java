package com.olegshan.mediabox.controller;
import com.olegshan.mediabox.model.Photo;
import com.olegshan.mediabox.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * Created by Bors on 04.06.2016.
 */
@Controller
public class EditController {

    @Autowired
    PhotoService photoService;

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id, Principal principal) {
        Photo photo = photoService.findOne(id);
        // Preventing manual typing of not own photo id
        try {
            if (photo.getUserClient().getName().equals(principal.getName())) {
                model.addAttribute("photoEdit", photo);
                return "edit";
            } else return "redirect:/";
        } catch (NullPointerException e) {
            return "redirect:/login";
        }
    }

    @RequestMapping("/edit/update/{id}")
    public String updatePhoto(@PathVariable int id,
                              @RequestParam("name") String name,
                              @RequestParam("tags") String tags,
                              @RequestParam("source") String source,
                              RedirectAttributes redirectAttributes) {

        Photo photo = photoService.findOne(id);
        photo.setName(name);
        photo.setTags(tags);
        photo.setSource(source);
        photoService.update(photo);

        redirectAttributes.addFlashAttribute("edited", "success");
        return "redirect:/edit/{id}";
    }

    @RequestMapping(value = {"/delete/{id}", "/edit/delete/{id}"})
    public String deletePhoto(@PathVariable int id) {
        Photo photo = photoService.findOne(id);
        photoService.delete(photo);
        return "redirect:/";
    }

    @RequestMapping("/edit/cancel")
    public String cancel() {
        return "redirect:/";
    }
}
