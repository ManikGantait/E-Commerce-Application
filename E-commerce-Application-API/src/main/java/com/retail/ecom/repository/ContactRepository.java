package com.retail.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
