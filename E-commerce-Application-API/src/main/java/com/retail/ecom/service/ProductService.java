package com.retail.ecom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.entity.Product;
import com.retail.ecom.request_dto.ProductRequest;
import com.retail.ecom.request_dto.SearchFilter;
import com.retail.ecom.response_dto.ProductResponse;
import com.retail.ecom.utility.ResponseStructure;

public interface ProductService {

	ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest);

	ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest,int productId);

	ResponseEntity<ResponseStructure<ProductResponse>> findProductById(int productId);
	ResponseEntity<ResponseStructure<List<ProductResponse>>> findAll(SearchFilter searchFilter,int page,String orderBy,String sortBy);

	ResponseEntity<ResponseStructure<List<ProductResponse>>> searchProduct(String serachText);

}
