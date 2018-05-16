//package com.springproject.webformtrialwiths3.Controller;
//
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.UUID;
//
//@Controller
//public class UploadController {
//
//    @RequestMapping(method = RequestMethod.POST, value = "/upload")
//    public String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
//
//        AmazonS3 s3 = new AmazonS3Client(new ProfileCredentialProvider());
//
//        String bucketName = "renda-"+ UUID.randomUUID();
//        s3.createBucket(bucketName);
//
//        try {
//            InputStream is = file.getInputStream();
//
//            s3.putObject(new PutObjectRequest(bucketName, name, is,new ObjectMetadata()).withCannedAcl((CannedAccessControlList.PublicRead));
//
//            S3Object s3Object = s3.getObject(new GetObjectRequest(bucketName, name));
//
//            redirectAttributes.addAttribute("picUrl", s3Object.getObjectContent().getHttpRequest().getURI().toString());
//
//            System.out.println(s3Object.getObjectContent().getHttpRequest().getURI().toString());
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//        return "redirect:/person";
//    }
//
//
//}
