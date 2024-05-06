package com.retail.ecom.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.retail.ecom.entity.Image;
import com.retail.ecom.enums.ImageType;

public interface ImageRepository extends MongoRepository<Image,String> {

	Optional<Image> findByProductIdAndImageType(int prodctId, ImageType cover);

}
