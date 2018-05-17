package com.springproject.gab_photography.Controller;

import com.springproject.gab_photography.Dao.ImageRepository;
import com.springproject.gab_photography.Entity.Image;
import com.springproject.gab_photography.Service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class DatabaseController {


    @Autowired
    private ImageRepository ir;


    private AmazonClient amazonClient;

    @Autowired
    DatabaseController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @GetMapping("/uploadFile")
    public ModelAndView uploadform(){
        ModelAndView page = new ModelAndView("uploadForm");
        return page;
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file,
                             @RequestParam(name = "id", defaultValue = "-1") int id,
                             @RequestParam(name = "name", defaultValue = "no_name") String name) {

        String imageUrl = this.amazonClient.uploadFile(file, name);

        Image image = new Image(id, imageUrl, imageUrl);

        if(imageUrl.startsWith("ERROR")){
            return imageUrl;
        }else {
            image.setId(id);
            image.setS3link(imageUrl);
            image.setThumbnail(generateThumbnailname(imageUrl));
            ir.save(image);

            return imageUrl;
        }

    }




    @GetMapping("/deleteFile")
    public ModelAndView deleteFile(@RequestParam(name = "id") int id) {
        Image image = ir.findImageById(id);
        String url = image.getS3link();
        ir.delete(image);
        this.amazonClient.deleteFileFromS3Bucket(url);

        ModelAndView mv = new ModelAndView("imageGrid");
        mv.getModel().put("imageList", ir.findAll());
        return mv;
    }


    private String generateThumbnailname(String orignalname){

        String mainStr = orignalname;
        String subStr = "thumbnail_";
        String str = "bucket/";

        int pos_str = mainStr.indexOf(str)+str.length();

        StringBuilder thumbUrl = new StringBuilder(orignalname);
        thumbUrl.insert(pos_str, subStr);

        return thumbUrl.toString();
    }


}
