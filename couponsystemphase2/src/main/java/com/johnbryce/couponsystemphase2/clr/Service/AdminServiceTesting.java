package com.johnbryce.couponsystemphase2.clr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.johnbryce.couponsystemphase2.Bean.Company;
import com.johnbryce.couponsystemphase2.Bean.Customer;
import com.johnbryce.couponsystemphase2.Exception.AlreadyExistException;
import com.johnbryce.couponsystemphase2.Exception.CanNotChangeExeption;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Exception.OperationNotAllowedExeption;
import com.johnbryce.couponsystemphase2.Service.AdminService;
import com.johnbryce.couponsystemphase2.Utils.Headers;
import com.johnbryce.couponsystemphase2.Utils.Ido;

@Component
@Order(3)
public class AdminServiceTesting implements CommandLineRunner {

	@Autowired
	private AdminService adminService;

	@Override
	public void run(String... args) throws Exception {

	
		Headers.AdminServiceTesting();

		System.out.println("----------------bad logging...----------------");
		try {
			System.out.println(adminService.login("dana5555@gmail.com", "111"));
		} catch (NotExistException e3) {
			System.out.println(e3.getMessage());
		}

		System.out.println("----------------good logging...----------------");
		try {
			System.out.println(adminService.login("admin@admin.com", "admin"));
		} catch (NotExistException e3) {
			System.out.println(e3.getMessage());
		}

		System.out.println("----------------add company works----------------");
		try {
			adminService.addCompany(new Company("CCC", "ccc@gmail.com", "ccc"));
			Ido.print(adminService.getOneCompany(5));
		} catch (AlreadyExistException | NotExistException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("----------------add company with the same name should not work----------------");
		try {
			adminService.addCompany(new Company("JB", "jjjB@gmail.com", "*6460"));
		} catch (AlreadyExistException | NotExistException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("----------------add company with the same email address should not work----------------");
		try {
			adminService.addCompany(new Company("Fit Studio", "fit5@gmail.com", "fitfitfit"));
		} catch (AlreadyExistException | NotExistException e2) {
			System.out.println(e2.getMessage());
		}
		System.out.println("----------------update company id should not work----------------");
		Company company;
		try {
			company = adminService.getOneCompany(1);
			company.setId(3);
			adminService.updateCompany(company);
		} catch (NotExistException | CanNotChangeExeption e2) {
			System.out.println(e2.getMessage());
		}
		System.out.println("----------------update company name should not work----------------");
		try {
			company = adminService.getOneCompany(1);
			company.setName("Sahut Tari");
			adminService.updateCompany(company);
		} catch (NotExistException | CanNotChangeExeption e2) {
			System.out.println(e2.getMessage());
		}
		System.out.println("----------------update company works----------------");
		try {
			company = adminService.getOneCompany(1);
			company.setPassword("sahuttari123");
			adminService.updateCompany(company);
			Ido.print(adminService.getOneCompany(1));
		} catch (NotExistException | CanNotChangeExeption e2) {
			System.out.println(e2.getMessage());
		}
		System.out.println("----------------delete company works----------------");
		adminService.deleteCompany(2);
		System.out.println("----------------get all companies----------------");
		try {
			Ido.print(adminService.getCompanies());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("----------------get one company by id----------------");
		try {
			Ido.print(adminService.getOneCompany(1));
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

		System.out.println("----------------add customer with the same email adress should not work----------------");
		try {
			adminService.addCustomer(new Customer("Dana", "Ram", "dana5@gmail.com", "DanaDana", null));
		} catch (AlreadyExistException | NotExistException e1) {
			System.out.println(e1.getMessage());
		}
		System.out.println("----------------add customer works----------------");
		try {
			adminService.addCustomer(
					new Customer("Natalie", "Drosvit", "nataliedrosvit@gmail.com", "nataliedrosvit", null));
			Ido.print(adminService.getCustomers());
		} catch (AlreadyExistException | NotExistException e1) {
			System.out.println(e1.getMessage());
		}
		System.out.println("----------------update customer id should not work----------------");
		Customer customer;

		try {
			customer = adminService.getOneCustomer(2);
			customer.setId(3);
			adminService.updateCustomer(customer);
			Ido.print(adminService.getOneCustomer(2));
		} catch (OperationNotAllowedExeption e2) {
			System.out.println(e2.getMessage());
		}
		System.out.println("----------------update customer works----------------");
		try {
			Customer customer2 = adminService.getOneCustomer(2);
			customer2.setLastName("Zehavi");
			adminService.updateCustomer(customer2);
			Ido.print(adminService.getOneCustomer(2));
		} catch (OperationNotAllowedExeption e2) {
			System.out.println(e2.getMessage());
		}
		System.out.println("----------------delete customer works----------------");
		adminService.deleteCustomer(1);
		Ido.print(adminService.getCustomers());
		System.out.println("----------------get all customers----------------");
		Ido.print(adminService.getCustomers());
		System.out.println("----------------get one customer by id works----------------");
		Ido.print(adminService.getOneCustomer(3));
	}
}
