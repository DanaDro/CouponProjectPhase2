package com.johnbryce.couponsystemphase2.Service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.johnbryce.couponsystemphase2.Bean.Company;
import com.johnbryce.couponsystemphase2.Bean.Coupon;
import com.johnbryce.couponsystemphase2.Bean.Customer;
import com.johnbryce.couponsystemphase2.Exception.AlreadyExistException;
import com.johnbryce.couponsystemphase2.Exception.CanNotChangeExeption;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Exception.OperationNotAllowedExeption;


@Service
@Lazy
public class AdminService extends ClientService {

	
	private static final String EMAIL = "admin@admin.com";
	private static final String PASSWORD = "admin";

	public boolean login(String email, String password) throws NotExistException {
		if (email.equals(EMAIL) && password.equals(PASSWORD)) {
			return true;
		}
		throw new NotExistException("Email or Password");
	}

	public void addCompany(Company company) throws AlreadyExistException, NotExistException {
		List<Company> companys = companyRepository.findAll();
		for (Company comp : companys) {
			if (comp.getEmail().equals(company.getEmail())) {
				throw new AlreadyExistException("email");
			}
			if (comp.getName().equals(company.getName())) {
				throw new AlreadyExistException("name");
			}
		}
		companyRepository.save(company);
	}

	public void updateCompany(Company company) throws NotExistException, CanNotChangeExeption {
		List<Company> companies = companyRepository.findAll();
		for (Company comp : companies) {
			if (comp.getName().equals(company.getName()) && comp.getId() == company.getId()) {
				companyRepository.saveAndFlush(company);
				return;
			}
		}
		throw new CanNotChangeExeption("Company's name or Id");
	}

	public void deleteCompany(int companyId) throws CanNotChangeExeption {
		List<Coupon> companyCoupons = companyRepository.getOne(companyId).getCoupons();
		for (int i = 0; i < companyCoupons.size(); i++) {
			deleteCouponForCustomers(companyCoupons.get(i));
		}
		couponRepository.deleteAll(companyCoupons);
		companyRepository.deleteById(companyId);

	}

	public void deleteCouponForCustomers(Coupon coupon) {
		List<Customer> customers = getCustomers();
		for (Customer cust : customers) {
			List<Coupon> customerCoupons = cust.getCoupons();
			for (int i = 0; i < customerCoupons.size(); i++) {
				Coupon coup = customerCoupons.get(i);
				if (coup.getId() == coupon.getId()) {
					customerCoupons.remove(i);
				}
			}
			cust.setCoupons(customerCoupons);
			customerRepository.saveAndFlush(cust);
		}
	}

	public List<Company> getCompanies() {
		List<Company> company = companyRepository.findAll();
		return company;
	}

	public Company getOneCompany(int companyId) {
		return companyRepository.getOne(companyId);
	}

	public void addCustomer(Customer customer) throws AlreadyExistException, NotExistException {
		List<Customer> customers = getCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail())) {
				throw new AlreadyExistException("email");
			}
		}
		this.customerRepository.save(customer);
	}

	public void updateCustomer(Customer customer) throws NotExistException, OperationNotAllowedExeption {
		Customer cust = customerRepository.getOne(customer.getId());
		if (cust == null) {
			throw new NotExistException("Customer");
		}
		customerRepository.saveAndFlush(customer);
	}

	public void deleteCustomer(int customerId) {
		customerRepository.deleteById(customerId);
	}

	public List<Customer> getCustomers() {
		List<Customer> customers = customerRepository.findAll();
		for (int i = 0; i < customers.size(); i++) {
			customers.set(i, getOneCustomer(customers.get(i).getId()));
		}
		return customers;
	}

	public Customer getOneCustomer(int customerId) {
		Customer customer = customerRepository.getOne(customerId);
		return customer;
	}
}
