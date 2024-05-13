package com.retail.ecom.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.retail.ecom.entity.Product;
import com.retail.ecom.request_dto.SearchFilter;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductSpecifications {

	private SearchFilter searchFilter;

	public Specification<Product> buildSpecification() {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (searchFilter.getMinPrice() > 0)
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("productPrice"),searchFilter.getMinPrice()));
			
			if (searchFilter.getMaxPrice() > 0)
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("productPrice"),searchFilter.getMaxPrice()));
			
			if(searchFilter.getCatagory()!=null)
				predicates.add(criteriaBuilder.equal(root.get("productCatagory"),searchFilter.getCatagory()));
			if(searchFilter.getAvailability()!=null)
				predicates.add(criteriaBuilder.equal(root.get("availabilityStatus"),searchFilter.getAvailability()));
			if(searchFilter.getRating()>0)
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"),searchFilter.getRating()));
			if(searchFilter.getDiscount()>0)
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discount"),searchFilter.getDiscount()));

			
			return criteriaBuilder.and( predicates.toArray(new Predicate[0]));

		};

	}

}
