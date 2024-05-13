package com.retail.ecom.serviceimple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.retail.ecom.entity.CartProduct;
import com.retail.ecom.entity.Customer;
import com.retail.ecom.entity.User;
import com.retail.ecom.exception.CartProductNotFoundByIdException;
import com.retail.ecom.exception.ProductNotFoundByIdException;
import com.retail.ecom.repository.CartProductRepository;
import com.retail.ecom.repository.ProductRepository;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.response_dto.CartProductResponse;
import com.retail.ecom.service.CartProductService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartProductServiceImpl implements CartProductService {
	
	private CartProductRepository cartProductRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;

	@Override
	public ResponseEntity<SimpleResponseStructure> cartProduct(int productId, int selectedQuantity) {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		productRepository.findById(productId).map(product->{
			CartProduct cartProduct = cartProductRepository.save(new CartProduct(product));
			
			userRepository.findByUsername(username).ifPresent(user ->{
				
				cartProduct.setCustomer((Customer)user);
				cartProduct.setSelectedQuantity(selectedQuantity);
				});
			return cartProductRepository.save(cartProduct);
			
		}).orElseThrow(()->new ProductNotFoundByIdException("product not found"));
		return ResponseEntity.ok(new SimpleResponseStructure()
				.setMessage("Product added to the cart")
				.setStatus(HttpStatus.OK.value()));
	}

	@Override
	public ResponseEntity<ResponseStructure<List<CartProductResponse>>> getCartProduct() {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		System.err.println(username);
		List<CartProduct> list = userRepository.findByUsername(username).map(user->{
			return cartProductRepository.findAllByCustomer((Customer)user);
		}).get();
		return ResponseEntity.ok(new ResponseStructure<List<CartProductResponse>>()
				.setData(mapToCartProductResponse(list))
				.setMessage("Product Found")
				.setStatusCode(HttpStatus.OK.value()));
	} 
	@Override
	public ResponseEntity<ResponseStructure<CartProductResponse>> updateQuentity(int cartProductId,int selectedQuantity) {

		CartProduct cartProduct = cartProductRepository.findById(cartProductId).map(carProduct->{
			carProduct.setSelectedQuantity(selectedQuantity);
			return cartProductRepository.save(carProduct);
		}).orElseThrow(()->new CartProductNotFoundByIdException("cart product not found"));
		return ResponseEntity.ok(new ResponseStructure<CartProductResponse>()
				.setData(mapToCartProductResponse(cartProduct))
				.setMessage("updated")
				.setStatusCode(HttpStatus.OK.value()));

	}
	@Override
	public ResponseEntity<SimpleResponseStructure> removeCartProduct(int cartProductid) {
		
		return cartProductRepository.findById(cartProductid).map(cartProduct->{
			cartProductRepository.delete(cartProduct);
			return ResponseEntity.ok().body(new SimpleResponseStructure()
											.setMessage("Product Removed")
											.setStatus(HttpStatus.OK.value()));
		}).orElseThrow(()->new CartProductNotFoundByIdException("cart not Found By Id"));
		
	}
	
	
	private List<CartProductResponse> mapToCartProductResponse(List<CartProduct> list)
	{
		List< CartProductResponse> cartProductResponses=new ArrayList<>();
		for(CartProduct cartProduct:list)
		{
			System.err.println(cartProduct);
			cartProductResponses.add(mapToCartProductResponse(cartProduct));
		}
		return cartProductResponses;
	}

	private CartProductResponse mapToCartProductResponse(CartProduct cartProduct) {
		
		 CartProductResponse cartProductResponse = CartProductResponse
				.builder()
				.cartProductId(cartProduct.getCartProductId())
				.selectedQuantity(cartProduct.getSelectedQuantity())
				.customerId(cartProduct.getCustomer().getUserId())
				.productId(cartProduct.getProduct().getProductId())
				.build();
		 System.out.println(cartProductResponse);
		 return cartProductResponse;
	}

	

	
	
	

}
