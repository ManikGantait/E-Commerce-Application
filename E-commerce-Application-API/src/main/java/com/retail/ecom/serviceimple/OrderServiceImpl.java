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
import com.retail.ecom.entity.Order;
import com.retail.ecom.entity.Product;
import com.retail.ecom.entity.User;
import com.retail.ecom.enums.OrderStatus;
import com.retail.ecom.exception.AddressNotFoundByIdException;
import com.retail.ecom.exception.OrderNotFoundByIdException;
import com.retail.ecom.exception.ProductNotAvailableException;
import com.retail.ecom.repository.AddressRepository;
import com.retail.ecom.repository.CartProductRepository;
import com.retail.ecom.repository.OrderRepository;
import com.retail.ecom.repository.ProductRepository;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.response_dto.OrderResponse;
import com.retail.ecom.service.OrderService;
import com.retail.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private CartProductRepository cartProductRepository;
	private ProductRepository productRepository;

	@Override
	public ResponseEntity<ResponseStructure<List<OrderResponse>>> order(int addressId) {
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<Order> placedOrder = addressRepository.findById(addressId).map(address->{	
			List<Order> orders =new ArrayList<>();
			
			userRepository.findByUsername(username).ifPresent(user->{
				List<CartProduct> list = cartProductRepository.findAllByCustomer((Customer)user);
				
				for(CartProduct cartProduct:list)								
				{
					 productRepository.findById(cartProduct.getCartProductId()).ifPresent(product->{
						 if(product.getProductQuantity()<cartProduct.getSelectedQuantity())
							 throw new ProductNotAvailableException("Plase Select less quentity");
						 product.setProductQuantity(product.getProductQuantity()-cartProduct.getCartProductId());
						 productRepository.save(product);						 
					 });
					 
					
					Order order= orderRepository.save(new Order());				
					order.setAddress(address);
					order.setCustomer(cartProduct.getCustomer());
					order.setProduct(cartProduct.getProduct());
					order.setOrderStatus(OrderStatus.PROCESSING);
					order.setSelectedQuantity(cartProduct.getSelectedQuantity());
					order=orderRepository.save(order);
					cartProductRepository.delete(cartProduct);
					
					orders.add(order);
				}
			});
			return orders;			
		}).orElseThrow(()->new AddressNotFoundByIdException("address not found"));
		
				return ResponseEntity.ok(new ResponseStructure<List<OrderResponse>>()
						.setData(mapToOrderResponse(placedOrder))
						.setMessage("Order placed")
						.setStatusCode(HttpStatus.OK.value()));
	}
	@Override
	public ResponseEntity<ResponseStructure<OrderResponse>> cancleOrder(int orderId) {
		 return orderRepository.findById(orderId).map(order->{
			 if(order.getOrderStatus().equals(OrderStatus.CANCLE))
			 {
				 return ResponseEntity.ok()
							.body(new ResponseStructure<OrderResponse>()
											.setData(mapToOrderResponse(order))
											.setMessage("Order Already Cancled")
											.setStatusCode(HttpStatus.OK.value()));
			 }
			order.setOrderStatus(OrderStatus.CANCLE);
			
			return ResponseEntity.ok()
					.body(new ResponseStructure<OrderResponse>()
									.setData(mapToOrderResponse(orderRepository.save(order)))
									.setMessage("Order Cancled")
									.setStatusCode(HttpStatus.OK.value()));
			
		}).orElseThrow(()->new OrderNotFoundByIdException("order not found"));
	}
	
	@Override
	public ResponseEntity<ResponseStructure<List<OrderResponse>>> orderHistory() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUsername(username).map(user->{
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseStructure<List<OrderResponse>>()
							.setData(orderRepository.findAllByCustomer((Customer)user)
										.stream().map(this::mapToOrderResponse).toList()
										)
							.setMessage("Order details")
							.setStatusCode(HttpStatus.OK.value()));			
		}).get();
	}
	@Override
	public ResponseEntity<ResponseStructure<OrderResponse>> tackOrders(int orderId) {
		
		return ResponseEntity.ok()
		.body(new ResponseStructure<OrderResponse>()
					.setData(orderRepository
							.findById(orderId).map(this::mapToOrderResponse)
											.orElseThrow(()->new OrderNotFoundByIdException("order Not Found By Id")))
					.setMessage("Order Details")
					.setStatusCode(HttpStatus.OK.value()));
		}	
	
	
	
	private List<OrderResponse> mapToOrderResponse(List<Order> orders)
	{
		List< OrderResponse>  orderResponses=new ArrayList<>();
		for(Order order: orders)
		{
			orderResponses.add(mapToOrderResponse(order));
		}
		return orderResponses;
	}
	
	private OrderResponse mapToOrderResponse(Order order)
	{
		return OrderResponse.builder()
				.orderId(order.getOrderId())
				.selectedQuantity(order.getSelectedQuantity())
				.totalPrice(order.getTotalPrice())
				.totalPayabelAmount(order.getTotalPayabelAmount())
				.discountPrice(order.getDiscountPrice())
				.addressId(order.getAddress().getAddressId())
				.customerId(order.getCustomer().getUserId())
				.productId(order.getProduct().getProductId())
				.orderStatus(order.getOrderStatus())
				.build();
	}
	
	
	
}
