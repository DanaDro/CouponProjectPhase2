package com.johnbryce.couponsystemphase2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Repo.CompanyRepository;
import com.johnbryce.couponsystemphase2.Repo.CouponRepository;
import com.johnbryce.couponsystemphase2.Repo.CustomerRepository;

import lombok.Data;

@Service
@Data
public abstract class ClientService {
	
	@Autowired
	 protected CompanyRepository companyRepository;
	@Autowired
	 protected CustomerRepository customerRepository;
	@Autowired
	 protected CouponRepository couponRepository;

	public abstract boolean login(String email, String password) throws NotExistException;
}
