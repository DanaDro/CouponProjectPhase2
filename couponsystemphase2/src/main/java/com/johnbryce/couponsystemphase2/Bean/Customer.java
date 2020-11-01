package com.johnbryce.couponsystemphase2.Bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.johnbryce.couponsystemphase2.Exception.OperationNotAllowedExeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "customers")

public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Coupon> coupons = new ArrayList<>();
	
	public Customer(String firstName, String lastName, String email, String password, List<Coupon> coupons) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}

	public void setId(int id) throws OperationNotAllowedExeption {
		throw new OperationNotAllowedExeption("Change Id");
	}	

}
