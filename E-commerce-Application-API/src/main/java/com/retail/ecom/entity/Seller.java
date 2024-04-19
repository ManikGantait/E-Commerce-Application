package com.retail.ecom.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
//@DiscriminatorValue("seller")
public class Seller extends User{

}
