package com.johnbryce.couponsystemphase2.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.couponsystemphase2.Bean.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
