package com.olegshan.mediabox.controller;

import com.olegshan.mediabox.model.Photo;
import com.olegshan.mediabox.service.AmazonS3Service;
import com.olegshan.mediabox.service.PhotoService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Bors on 14.05.2016.
 */
@Controller
public class UploadController {

    private static final String SUFFIX = "/";

    @Autowired
    private PhotoService photoService;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @RequestMapping("/picture-upload")
    public String pictureUpload() {
        return "picture-upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadMultiple(Principal principal,
                                 @RequestParam("file") MultipartFile[] files,
                                 @RequestParam(value = "name", required = false) String[] names,
                                 @RequestParam(value = "tags", required = false) String[] tags,
                                 @RequestParam(value = "source", required = false) String[] sources,
                                 RedirectAttributes redirectAttributes) {

        // Did user choose any files?
        if (files[0].isEmpty()) {
            redirectAttributes.addFlashAttribute("photolist", "blank");
            return "redirect:/picture-upload";
        }

        for (MultipartFile f : files) {
            // Does user try to load only pictures?
            String ext = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".") + 1);
            if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png") && !ext.equals("gif")) {
                redirectAttributes.addFlashAttribute("filetype", "wrong");
                return "redirect:/picture-upload";
            }
            // Are the files not too large?
            if (f.getSize() > 1024 * 1024 * 8) {
                redirectAttributes.addFlashAttribute("maxsize", "exceeded");
                return "redirect:/picture-upload";
            }
        }

        // Create main and thumbnail folders
        String folderName = principal.getName() + "-folder";
        amazonS3Service.createFolder(folderName);


        String thumbnailFolderName = folderName + SUFFIX + "thumbnails";
        amazonS3Service.createFolder(thumbnailFolderName);

        for (int i = 0; i < files.length; i++) {

            MultipartFile file = files[i];
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + i + ext;

            try {

                // Upload the image file to the S3 server
                File fileToUpload = new File(fileName);
                file.transferTo(fileToUpload);
                String fileNameToUpload = folderName + SUFFIX + fileName;
                amazonS3Service.upload(fileNameToUpload, fileToUpload);

                // Create thumbnail and upload it to the S3 server
                File thumbnailToUpload = new File(fileName);
                String thumbnailNameToUpload = thumbnailFolderName + SUFFIX + fileName;
                Thumbnails.Builder<File> thumbnail = Thumbnails.of(fileToUpload).size(200, 200);
                thumbnail.toFile(thumbnailToUpload);
                amazonS3Service.upload(thumbnailNameToUpload, thumbnailToUpload);

                // Somehow, when user chooses only one photo, the length of all fields is 0. So we need to check
                // the length of each array before we check [i] element, otherwise we will became the NullPointerException
                String name = "undescribed";

                if (names.length != 0) {
                    if (names[i].trim().length() != 0) {
                        name = names[i];
                    }
                }

                String tag = "untagged";

                if (tags.length != 0) {
                    if (tags[i].trim().length() != 0) {
                        tag = tags[i];
                    }
                }

                String source = "nosource";

                if (sources.length != 0) {
                    if (sources[i].trim().length() != 0) {
                        source = sources[i];
                    }
                }

                // Save photo to the database with all the fields
                Photo photo = new Photo();
                photo.setName(name);
                photo.setTags(tag);
                photo.setSource(source);
                photo.setPublishedDate(new Date());
                photo.setLocation(amazonS3Service.getLocation(fileNameToUpload));
                photo.setThumbnailPath(amazonS3Service.getLocation(thumbnailNameToUpload));
                photoService.save(photo, principal.getName());

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("success", false);
                return "redirect:/picture-upload";
            }
        }

        return "redirect:/";
    }
}
