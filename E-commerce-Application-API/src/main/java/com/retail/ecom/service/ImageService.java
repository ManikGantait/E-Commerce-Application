package com.retail.ecom.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.retail.ecom.entity.Image;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

public interface ImageService {

	public ResponseEntity<SimpleResponseStructure> addImage(int prodctId, String imageType, MultipartFile image) throws IOException;

	public ResponseEntity<byte[]> getImage( String imageId);

}
