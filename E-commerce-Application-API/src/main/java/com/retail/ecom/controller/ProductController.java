package com.retail.ecom.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.entity.Product;
import com.retail.ecom.request_dto.ProductRequest;
import com.retail.ecom.request_dto.SearchFilter;
import com.retail.ecom.response_dto.ProductResponse;
import com.retail.ecom.service.ProductService;
import com.retail.ecom.utility.ProductSpecifications;
import com.retail.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProductController {
	
	private ProductService productService;
	
	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products")
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest )
	{
		return productService.addProduct(productRequest);
	}
	
	@PreAuthorize("hasAuthority('SELLER')")
	@PutMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(@RequestBody ProductRequest productRequest ,@PathVariable int productId )
	{
		System.out.println(productId);
		return productService.updateProduct(productRequest,productId);
	}
	@GetMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> findProductById(@PathVariable int productId)
	{
		return productService.findProductById(productId);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getfilterProduct(SearchFilter searchFilter,int page,String orderBy, String sortBy)
	{
		return productService.findAll(searchFilter,page,orderBy,sortBy);
	}
	@GetMapping("/products")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> searchProduct(@RequestParam String searchText)
	{
		return productService.searchProduct(searchText);
	}

}
