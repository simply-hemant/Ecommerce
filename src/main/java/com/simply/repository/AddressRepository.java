package com.simply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simply.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
