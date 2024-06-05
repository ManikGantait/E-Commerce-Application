package com.retail.ecom.serviceimple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.retail.ecom.entity.Image;
import com.retail.ecom.entity.Product;
import com.retail.ecom.entity.Seller;
import com.retail.ecom.enums.AvailabilityStatus;
import com.retail.ecom.enums.ImageType;
import com.retail.ecom.enums.OrderDirection;
import com.retail.ecom.exception.ProductNotFoundByIdException;
import com.retail.ecom.exception.UserNotFoundByUsernameException;
import com.retail.ecom.jwt.JwtService;
import com.retail.ecom.repository.ImageRepository;
import com.retail.ecom.repository.ProductRepository;
import com.retail.ecom.repository.SellerRepository;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.request_dto.ProductRequest;
import com.retail.ecom.request_dto.SearchFilter;
import com.retail.ecom.response_dto.ProductResponse;
import com.retail.ecom.service.ProductService;
import com.retail.ecom.utility.ProductSpecifications;
import com.retail.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	private ProductRepository productRepository;
	private UserRepository userRepository;
	private SellerRepository sellerRepository;
	private ImageRepository imageRepository;

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByUsername(name).map(user -> {
			
			Product product = productRepository.save(mapToProduct(new Product(), productRequest));
			Seller seller = ((Seller) user);
			seller.getProducts().add(product);
			sellerRepository.save(seller);
			return ResponseEntity.ok()
					.body(new ResponseStructure<ProductResponse>().setData(mapToProductResponse(product))
							.setMessage("product added").setStatusCode(HttpStatus.OK.value()));
		}).orElseThrow(()-> new UserNotFoundByUsernameException("user not found by username"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest,int productId) {
		
		return productRepository.findById(productId).map(product->{
			Product product2 = productRepository.save(mapToProduct(product, productRequest));
				return ResponseEntity.ok()
						.body(new ResponseStructure<ProductResponse>().setData(mapToProductResponse(product2))
								.setMessage("product updated").setStatusCode(HttpStatus.OK.value()));
				
			}).get();
			
	}
	
	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> findProductById(int productId) {
		return productRepository.findById(productId).map(product->{
			return ResponseEntity.ok()
					.body(new ResponseStructure<ProductResponse>().setData(mapToProductResponse(product))
							.setMessage("product updated").setStatusCode(HttpStatus.OK.value()));
		}).orElseThrow(()->new ProductNotFoundByIdException("product with this id not present"));
	}
	
	@Override
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> findAll(SearchFilter searchFilter,int page,String oderBy,String sortBy) {
		Specification<Product> specification = new ProductSpecifications(searchFilter).buildSpecification();
		Pageable pageable=pageable=(Pageable) PageRequest.of(page, 2);;
		if(oderBy!=null && sortBy!=null)
		{
			if(OrderDirection.ASC.equals(oderBy.toUpperCase()))
				 pageable=(Pageable) PageRequest.of(page, 2,Sort.by(Direction.ASC,sortBy));
				if(OrderDirection.DESC.equals(oderBy.toUpperCase()))
					 pageable=(Pageable) PageRequest.of(page, 2,Sort.by(Direction.DESC,sortBy));
		}
		
		Page<Product> page2 = productRepository.findAll(specification,pageable);		
		List<Product> productList=page2.getContent();
		System.out.println(productList);
		return ResponseEntity.ok(new ResponseStructure<List<ProductResponse>>()
				.setData(mapToProductResponse(productList))
				.setMessage("found").setStatusCode(HttpStatus.OK.value()));
	}
	
	
	@Override
	public ResponseEntity<ResponseStructure<List<ProductResponse>>>  searchProduct(String serachText) {
		List<Product> products= productRepository.findByProductNameIgnoreCaseContainingOrProductDescriptionIgnoreCaseContaining(serachText,serachText);
		return ResponseEntity.ok(new ResponseStructure<List<ProductResponse>>()
				.setData(mapToProductResponse(products))
				.setMessage("found").setStatusCode(HttpStatus.OK.value())); 
	}


	private List<ProductResponse> mapToProductResponse(List<Product> products)
	{
		List<ProductResponse> productResponses=new ArrayList<>();
		for(Product product:products)
		{
			productResponses.add(mapToProductResponse(product));
		}
		return productResponses;
	}
	
	private Product mapToProduct(Product product, ProductRequest productRequest) {

		product.setProductName(productRequest.getProductName());
		product.setProductCatagory(productRequest.getProductCatagory());
		product.setProductDescription(productRequest.getProductDescription());
		product.setProductPrice(productRequest.getProductPrice());
		product.setProductQuantity(productRequest.getProductQuantity());
		if(productRequest.getProductQuantity()>=10)
			product.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
		else if(productRequest.getProductQuantity()<10 &&productRequest.getProductQuantity()>0)
			product.setAvailabilityStatus(AvailabilityStatus.ONLYFEWLEFT);
		else
			product.setAvailabilityStatus(AvailabilityStatus.OUTOFSTOCK);
		
		return product;
	}
	

	private ProductResponse mapToProductResponse(Product product) {
		
		
		return ProductResponse.builder()
				.productId(product.getProductId())
				.productName(product.getProductName())
				.productPrice(product.getProductPrice())
				.productQuantity(product.getProductQuantity())
				.productCatagory(product.getProductCatagory())
				.productDescription(product.getProductDescription())
				.availabilityStatus(product.getAvailabilityStatus())
				.coverImages(generateImageUrl(product.getProductId()))
				.build();
	}
	
	private String generateImageUrl(int productId)
	{
		Image image = imageRepository.findImageByProductIdAndImageType(productId, ImageType.COVER);
		if(image!=null)
			return "/api/v1/image/"+image.getImageId();
		 return null;
	}

	

	
	

}
