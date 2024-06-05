package com.retail.ecom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.response_dto.CartProductResponse;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

public interface CartProductService {

	ResponseEntity<SimpleResponseStructure> cartProduct(int productId, int selectedQuantity);

	ResponseEntity<ResponseStructure<List<CartProductResponse>>> getCartProduct();

	ResponseEntity<ResponseStructure<CartProductResponse>> updateQuentity(int cartProductId,int selectedQuantity);

	ResponseEntity<SimpleResponseStructure> removeCartProduct(int cartProductid);

}
