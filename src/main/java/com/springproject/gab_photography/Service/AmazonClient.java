package com.springproject.webformtrialwiths3.Service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("https://s3.us-west-2.amazonaws.com")
    private String endpointUrl;
    @Value("my-amazing-spring-bucket")
    private String bucketName;
    @Value("AKIAJOZ2S3OHDLU2ZGAA")
    private String accessKey;
    @Value("N+loGNwSJY9QEC+wDHeNHQV8NrWtpzsndmzGj5KO")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    public String uploadFile(MultipartFile multipartFile, String name) {
        String fileUrl = "";

        BufferedImage bufferedImage;



        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile, name);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();


                try {
                    bufferedImage = createThumbnail(multipartFile.getBytes());
                    File thumbnail = convertBufferedImageToFile(multipartFile,bufferedImage);
                    String thumName = "thumbnail_" + fileName;
                    uploadFileTos3bucket(thumName, thumbnail);
                    thumbnail.delete();

                }catch (IOException e){
                    System.out.println("thumbnail error");
                }

            return fileUrl;

        } catch (SdkClientException e) {
            return  "ERROR " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR " + e.getMessage();
        }

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private File convertBufferedImageToFile(MultipartFile file, BufferedImage bufferedImage) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        ImageIO.write(bufferedImage,"jpg",convFile);
        return convFile;
    }





    public BufferedImage createThumbnail(byte[] image) throws IOException {
        // Get a BufferedImage object from a byte array
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        // Get image dimensions
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        // The image is already a square
        if (height == width) {
            return originalImage;
        }

        // Compute the size of the square
        int squareSize = (height > width ? width : height);

        // Coordinates of the image's middle
        int xc = width / 2;
        int yc = height / 2;

        // Crop
        BufferedImage croppedImage = originalImage.getSubimage(
                xc - (squareSize / 2), // x coordinate of the upper-left corner
                yc - (squareSize / 2), // y coordinate of the upper-left corner
                squareSize,            // widht
                squareSize             // height
        );

        return croppedImage;
    }


    private String generateFileName(MultipartFile multipartFile,String name) {
        if(name.equals("no_name")){
            return new Date().getTime() + "-" + multipartFile.getOriginalFilename().replace(" ", "_");

        }else{
            return new Date().getTime() + "-" + name.replace(" ", "_");
        }
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

}