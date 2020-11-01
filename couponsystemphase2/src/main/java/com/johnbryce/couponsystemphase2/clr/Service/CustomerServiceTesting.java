package com.johnbryce.couponsystemphase2.clr.Service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.johnbryce.couponsystemphase2.Bean.Category;
import com.johnbryce.couponsystemphase2.Bean.Coupon;
import com.johnbryce.couponsystemphase2.Bean.Customer;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Repo.CouponRepository;
import com.johnbryce.couponsystemphase2.Repo.CustomerRepository;
import com.johnbryce.couponsystemphase2.Service.CustomerService;
import com.johnbryce.couponsystemphase2.Utils.Headers;
import com.johnbryce.couponsystemphase2.Utils.Ido;

@Component
@Order(2)
public class CustomerServiceTesting implements CommandLineRunner {

	@Autowired
	private CustomerService customerService;
	@Autowired
	static CouponRepository couponRepo;
	@Autowired
	private CustomerRepository customerRepo;
	
	@Override
	public void run(String... args) throws Exception {
		Headers.CustomerServiceTesting();
		Customer customer1 = new Customer("Dana", "Drosvit", "dana5@gmail.com", "111", null);
		Customer customer2 = new Customer("Ido", "Zahavy", "ido5@gmail.com", "555", null);
		Customer customer3 = new Customer("Daniel", "Ram", "danielram@gmail.com", "danielram", null);
		Customer customer4 = new Customer("Ido", "Shay", "idoshay@gmail.com", "343434", null);
		customerRepo.save(customer1);
		customerRepo.save(customer2);
		customerRepo.save(customer3);
		customerRepo.save(customer4);

		System.out.println("----------------bad logging...----------------");
		try {
			System.out.println(customerService.login("dana5555@gmail.com", "111"));
		} catch (NotExistException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("----------------good logging...----------------");
		try {
			System.out.println(customerService.login("danielram@gmail.com", "danielram"));
		} catch (NotExistException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("----------------add coupon works----------------");
		try {
			Coupon c2 = new Coupon(2, 1, Category.FOOD, "In n Out", "best burger in California",
					Date.valueOf("2020-09-20"), Date.valueOf("2021-09-20"), 1000, 50.5, "Burger");
			Coupon c3 = new Coupon(1, Category.ELECTRICITY, "Best Buy", "The smartest computer", Date.valueOf("2020-08-30"), Date.valueOf("2021-09-30"), 100, 4500, "15''");
			customerService.purchastCoupon(c2);
			customerService.purchastCoupon(c3);
			Ido.print(customerService.getCustomersCoupons());
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		System.out.println("----------------add coupon with 0 amount should not work----------------");
		try {
			Coupon c4 = new Coupon(4, 1, Category.FOOD, "Segev Restaurant", "Italian food", Date.valueOf("2020-09-09"),
					Date.valueOf("2020-09-30"), 0, 300, "pasta");
			customerService.purchastCoupon(c4);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		System.out.println("----------------add coupon with expired date should not work----------------");
		try {
			Coupon c1 = new Coupon(1, Category.VACATION, "The best Vaction", "Vaction in resort",
					Date.valueOf("2020-08-30"), Date.valueOf("2020-09-08"), 1000, 5555.9, "hotel B");
			customerService.purchastCoupon(c1);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		System.out.println("----------------add coupon existing should not work----------------");
		try {
			Coupon c2 = new Coupon(2, 1, Category.FOOD, "In n Out", "best burger in California",
					Date.valueOf("2020-09-20"), Date.valueOf("2021-09-20"), 1000, 50.5, "Burger");
			customerService.purchastCoupon(c2);
			Ido.print(customerService.getCustomersCoupons());
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		System.out.println("----------------get all coupons----------------");
		Ido.print(customerService.getCustomersCoupons());
		System.out.println("----------------get all coupons by category----------------");
		Ido.print(customerService.getCustomersCouponsByCategory(Category.FOOD));
		System.out.println("----------------get all coupons by max price----------------");
		Ido.print(customerService.getCustoemrCouponsByPrice(100.0));
		System.out.println("----------------get customer's detalis----------------");
		Ido.print(customerService.getCustomerDetalis());
	
	}
}
