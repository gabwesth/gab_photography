package com.springproject.gab_photography.Dao;

import com.springproject.gab_photography.Entity.Image;
import org.springframework.data.repository.CrudRepository;


public interface ImageRepository extends CrudRepository<Image, Integer> {
    public Image findImageById(int id);
}
