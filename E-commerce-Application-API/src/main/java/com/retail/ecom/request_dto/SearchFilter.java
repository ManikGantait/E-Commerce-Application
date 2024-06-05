package com.retail.ecom.request_dto;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchFilter {
	
	private int minPrice;
	private int maxPrice;
	private String availability;
	private int rating;
	private int discount;
	private String catagory;

}
