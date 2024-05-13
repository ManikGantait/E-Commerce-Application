package com.retail.ecom.serviceimple;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.retail.ecom.entity.Image;
import com.retail.ecom.enums.ImageType;
import com.retail.ecom.exception.ImageNotFoundByIdException;
import com.retail.ecom.exception.InvalidCredentialsException;
import com.retail.ecom.exception.ProductNotFoundByIdException;
import com.retail.ecom.repository.ImageRepository;
import com.retail.ecom.repository.ProductRepository;
import com.retail.ecom.service.ImageService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService{
	
	private ProductRepository productRepository;
	private ImageRepository imageRepository;

	@Override
	public ResponseEntity<SimpleResponseStructure> addImage(int prodctId, String imageType, MultipartFile image) throws IOException {
		
		
		if(!productRepository.existsById(prodctId))
			throw new ProductNotFoundByIdException("Product not found By Id");

		
		ImageType type = null;
		try {
			type = ImageType.valueOf(imageType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidCredentialsException("Invalid image type");
		}		
		if(type.equals(ImageType.COVER))
		imageRepository.findByProductIdAndImageType(prodctId,ImageType.COVER).ifPresent(imageData->{
			imageData.setImageType(ImageType.NORMAL);
			imageRepository.save(imageData);
			
		});		
		
			imageRepository.save(Image.builder()
					.imageBytes(image.getBytes())
					.productId(prodctId)
					.imageType(type)
					
					.contentType(image.getContentType())
					.build());		
		return ResponseEntity.ok(new SimpleResponseStructure()
				.setMessage("image added")
				.setStatus(HttpStatus.OK.value())); 
		
	}

	@Override
	public ResponseEntity<byte[]> getImage(String imageId) {
		return imageRepository.findById(imageId).map(image->{
			return 
			ResponseEntity.ok()
			.contentLength(image.getImageBytes().length)
			.contentType(MediaType.valueOf(image.getContentType()))
			.body(image.getImageBytes());
			
		}).orElseThrow(()->new ImageNotFoundByIdException("image not found"));
		
	}

	
}
