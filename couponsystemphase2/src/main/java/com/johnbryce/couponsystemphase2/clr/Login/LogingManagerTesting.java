package com.johnbryce.couponsystemphase2.clr.Login;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.johnbryce.couponsystemphase2.Security.ClientType;
import com.johnbryce.couponsystemphase2.Security.LoginManager;
import com.johnbryce.couponsystemphase2.Service.AdminService;
import com.johnbryce.couponsystemphase2.Service.CompanyService;
import com.johnbryce.couponsystemphase2.Service.CustomerService;
import com.johnbryce.couponsystemphase2.Utils.Headers;
import com.johnbryce.couponsystemphase2.Utils.Ido;

@Component
@Order(4)
public class LogingManagerTesting implements CommandLineRunner {

	@Autowired
	private LoginManager loginManager;
	
	@Override
	public void run(String... args) throws Exception {
		Headers.LogingManagerTesting();

		System.out.println("---------Company bad loging---------");
		CompanyService companyService = (CompanyService) loginManager.login("Sahut Tari", "sahut5@gmail.com",
				ClientType.COMPANY);
		if (companyService != null) {
			Ido.print(companyService.getCompanyDetails());
		}
		System.out.println("---------Company good loging---------");
		companyService = (CompanyService) loginManager.login("JB@gmail.com", "*6460", ClientType.COMPANY);
		System.out.println("Login successfully - Company usage example");
		if (companyService != null) {
			Ido.print(companyService.getCompanyDetails());
		}
		System.out.println("---------Customer bad loging---------");
		CustomerService customerService = (CustomerService) loginManager.login("daniel@gmail.com", "555",
				ClientType.CUSTOMER);
		if (customerService != null) {
			Ido.print(customerService.getCustomerDetalis());
		}
		System.out.println("---------Customer good loging---------");
		customerService = (CustomerService) loginManager.login("ido5@gmail.com", "555", ClientType.CUSTOMER);
		System.out.println("Login successfully - Customer usage example");
		if (customerService != null) {
			Ido.print(customerService.getCustomerDetalis());
		}
		System.out.println("---------Admin bad loging---------");
		AdminService administratorService = (AdminService) loginManager.login("Dana", "1234",
				ClientType.ADMINISTRATOR);
		if (administratorService != null) {
			Ido.print(administratorService.getCompanies());
			Ido.print(administratorService.getCustomers());
		}
		System.out.println("---------Admin good loging---------");
		administratorService = (AdminService) loginManager.login("admin@admin.com", "admin",
				ClientType.ADMINISTRATOR);
		System.out.println("Login successfully - Admin usage example");
		if (administratorService != null) {
			Ido.print(administratorService.getCompanies());
			Ido.print(administratorService.getCustomers());
		}
	}
}
