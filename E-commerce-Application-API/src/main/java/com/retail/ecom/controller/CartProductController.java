package com.retail.ecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.entity.CartProduct;
import com.retail.ecom.response_dto.CartProductResponse;
import com.retail.ecom.service.CartProductService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true" )
public class CartProductController {
	
	private CartProductService cartProductService;

	@PostMapping("/product/{productId}/cartproduct/{selectedQuantity}")
	public ResponseEntity<SimpleResponseStructure> cartProduct(@PathVariable int productId,@PathVariable int selectedQuantity)
	{
		return cartProductService.cartProduct(productId, selectedQuantity);
	}
	@GetMapping("/customer/cartproduct")
	public ResponseEntity<ResponseStructure<List<CartProductResponse>>> getCartProduct() {
		return cartProductService.getCartProduct();
	}
//	@PreAuthorize("hasAuthority('CUSTOMER')")
	@PatchMapping("/cartproduct/{cartProductId}/{selectedQuantity}")
	public  ResponseEntity<ResponseStructure<CartProductResponse>> updateQuentity(@PathVariable int cartProductId, @PathVariable int selectedQuantity)
	{
		return cartProductService.updateQuentity(cartProductId,selectedQuantity);
	}
	@DeleteMapping("/cartproduct/{cartProductId}")
	public ResponseEntity<SimpleResponseStructure> removeCartProduct(@PathVariable int cartProductId)
	{
		return cartProductService.removeCartProduct(cartProductId);
	}
	
	

}
