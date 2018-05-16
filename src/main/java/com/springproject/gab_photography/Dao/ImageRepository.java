package com.springproject.webformtrialwiths3.Dao;

import com.springproject.webformtrialwiths3.Entity.Image;
import org.springframework.data.repository.CrudRepository;


public interface ImageRepository extends CrudRepository<Image, Integer> {
    public Image findImageById(int id);
}
