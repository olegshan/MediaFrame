package com.olegshan.mediabox.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.olegshan.mediabox.model.Photo;
import com.olegshan.mediabox.repository.PhotoRepository;
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

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PhotoService photoService;

    private static final String SUFFIX = "/";

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

        //Did user choose any files?
        if (files[0].isEmpty()) {
            redirectAttributes.addFlashAttribute("photolist", "blank");
            return "redirect:/picture-upload";
        }

        // Are the files not too large?
        for (MultipartFile f : files) {
            if (f.getSize() > 1024 * 1024 * 8) {
                redirectAttributes.addFlashAttribute("maxsize", "exceeded");
                return "redirect:/picture-upload";
            }
        }

        //Connect to Amazon S3 server
        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
        AmazonS3 s3client = new AmazonS3Client(credentials);
        String bucketName = "mediaframe";

        // Check whether main and thumbnail folder exist. If not, create them
        String folderName = principal.getName() + "-folder";
        if (!s3client.doesObjectExist(bucketName, folderName)) {
            createFolder(bucketName, folderName, s3client);
        }

        String thumbnailFolderName = folderName + SUFFIX + "thumbnails";
        if (!s3client.doesObjectExist(bucketName, thumbnailFolderName)) {
            createFolder(bucketName, thumbnailFolderName, s3client);
        }

        for (int i = 0; i < files.length; i++) {

            MultipartFile file = files[i];
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + i + ext;

            try {

                // Upload the image file to the S3 server
                File fileToUpload = new File(fileName);
                file.transferTo(fileToUpload);
                String fileNameToUpload = folderName + SUFFIX + fileName;
                s3client.putObject(new PutObjectRequest(bucketName, fileNameToUpload, fileToUpload)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                // Create thumbnail and upload it to the S3 server
                File thumbnailToUpload = new File(fileName);
                String thumbnailNameToUpload = thumbnailFolderName + SUFFIX + fileName;
                Thumbnails.Builder<File> thumbnail = Thumbnails.of(fileToUpload).size(200, 200);
                thumbnail.toFile(thumbnailToUpload);
                s3client.putObject(new PutObjectRequest(bucketName, thumbnailNameToUpload, thumbnailToUpload)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                // Somehow, when user chooses only one photo, the length of all fields is 0. So we need to check
                // the length of each array before we check [i] element, otherwise we will became the NullPointerException
                String name = "unnamed";

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
                photo.setLocation(s3client.getUrl(bucketName, fileNameToUpload).toString());
                photo.setThumbnailPath(s3client.getUrl(bucketName, thumbnailNameToUpload).toString());
                photoService.save(photo, principal.getName());

            } catch (Exception e) {
                try {
                    PrintStream ps = new PrintStream("e:/logfile.log");
                    e.printStackTrace(ps);
                    ps.close();
                } catch (Exception e1) {
                }
                redirectAttributes.addFlashAttribute("success", false);
                return "redirect:/picture-upload";
            }

        }

        return "redirect:/";
    }

    //This method creates an empty folder in Amazon S3 bucket
    private static void createFolder(String bucketName, String folderName, AmazonS3 client) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                folderName + SUFFIX, emptyContent, metadata);
        client.putObject(putObjectRequest);
    }
}
