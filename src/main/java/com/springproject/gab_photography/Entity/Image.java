package com.springproject.gab_photography.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Image {

    private static List<Image> imageList = new ArrayList<>();

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public int id;
    public String S3link;
    public String thumbnail;

    public Image(int id, String s3link, String thumbnail) {
        this.id = id;
        this.S3link = s3link;
        this.thumbnail = thumbnail;
    }

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS3link() {
        return S3link;
    }

    public void setS3link(String s3link) {
        S3link = s3link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public static List<Image> getImageList() {
        return imageList;
    }

}
