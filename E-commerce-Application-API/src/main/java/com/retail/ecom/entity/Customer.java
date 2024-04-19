package com.retail.ecom.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
//@DiscriminatorValue("customer")
public class Customer extends User{

}
