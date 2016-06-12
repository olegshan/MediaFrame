package com.olegshan.mediabox.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.olegshan.mediabox.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

@Service
public class AmazonS3Service {

    @Autowired
    private PhotoService photoService;

    private static final String SUFFIX = "/";

    //Connect to Amazon S3 server
//    private static AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
    private static AWSCredentials credentials = new EnvironmentVariableCredentialsProvider().getCredentials();
    private static AmazonS3 s3client = new AmazonS3Client(credentials);
    private static String bucketName = "mediaframe";


    public void createFolder(String folderName) {
        if (!s3client.doesObjectExist(bucketName, folderName)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                    folderName + SUFFIX, emptyContent, metadata);
            s3client.putObject(putObjectRequest);
        }
    }

    public void upload(String fileName, File fileToUpload) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, fileToUpload)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getLocation(String fileName) {
        return s3client.getUrl(bucketName, fileName).toString();
    }

    public void delete(Photo photo) {
        Photo p = photoService.findOne(photo);
        // The part of photo url before the name has always length of 36 characters
        String file = p.getLocation().substring(36);
        String thumbnail = p.getThumbnailPath().substring(36);
        s3client.deleteObject(bucketName, file);
        s3client.deleteObject(bucketName, thumbnail);
    }
}
