package com.retail.ecom.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.retail.ecom.entity.Image;
import com.retail.ecom.service.ImageService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true" )
public class ImageController {
	
	private ImageService imageService;
	
	
	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products/{productId}/image-type/{imageType}/image")
	public ResponseEntity<SimpleResponseStructure> addImage(@PathVariable int productId,@PathVariable String imageType, MultipartFile image) throws IOException
	{
		return imageService.addImage(productId,imageType,image);
	}
	
	@GetMapping("/image/{imageId}")
	public ResponseEntity<byte[]> getImage( @PathVariable String imageId)
	{
		return imageService.getImage(imageId);
	}

}
