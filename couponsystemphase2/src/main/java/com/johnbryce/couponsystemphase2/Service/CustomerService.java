package com.johnbryce.couponsystemphase2.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.johnbryce.couponsystemphase2.Bean.Category;
import com.johnbryce.couponsystemphase2.Bean.Coupon;
import com.johnbryce.couponsystemphase2.Bean.Customer;
import com.johnbryce.couponsystemphase2.Exception.AlreadyExistException;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Exception.OperationNotAllowedExeption;


@Service
@Scope("prototype")
public class CustomerService extends ClientService {

	private int customerId;

	public boolean login(String email, String password) throws NotExistException {
		List<Customer> customers = customerRepository.findAll();
		for (Customer customer : customers) {
			if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
				this.customerId = customer.getId();
				return true;
			}
		}

		throw new NotExistException("Email or Password");
	}

	public void purchastCoupon(Coupon coupon) throws Exception {
		List<Coupon> coupons = getCustomersCoupons();
		for (Coupon coup : coupons) {
			if (coup.getId() == coupon.getId()) {
				throw new AlreadyExistException("coupon");
			}
		}
		if (coupon.getAmount() <= 0) {
			throw new OperationNotAllowedExeption("Purchase coupon with amount 0");
		}
		if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
			throw new OperationNotAllowedExeption("Purchase coupon expired");
		}
		coupon.setAmount(coupon.getAmount() - 1);
		couponRepository.saveAndFlush(coupon);
		Customer customer = customerRepository.getOne(customerId);
		customer.getCoupons().add(coupon);
		customerRepository.saveAndFlush(customer);
	}

	public List<Coupon> getCustomersCoupons() {
		Customer customer = customerRepository.getOne(customerId);
		return customer.getCoupons();
	}
	
	public List<Coupon> getCustomersCouponsByCategory(Category category) {
		List<Coupon> coupons = getCustomersCoupons();
		List<Coupon> results = new ArrayList<>();
		for (Coupon coupon : coupons) {
			if (coupon.getCategory().equals(category)) {
				results.add(coupon);
			}
		}
		return results;
	}

	public List<Coupon> getCustoemrCouponsByPrice(double maxPrice) {
		List<Coupon> coupons = getCustomersCoupons();
		List<Coupon> results = new ArrayList<>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() <= maxPrice) {
				results.add(coupon);
			}
		}
		return results;
	}

	public Customer getCustomerDetalis() {
		List<Coupon> customersCoupon = getCustomersCoupons();
		Customer customer = customerRepository.getOne(customerId);
		customer.setCoupons(customersCoupon);
		return customer;
	}
}
