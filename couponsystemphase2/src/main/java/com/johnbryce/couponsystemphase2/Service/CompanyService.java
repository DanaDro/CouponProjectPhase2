package com.johnbryce.couponsystemphase2.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.johnbryce.couponsystemphase2.Bean.Category;
import com.johnbryce.couponsystemphase2.Bean.Company;
import com.johnbryce.couponsystemphase2.Bean.Coupon;
import com.johnbryce.couponsystemphase2.Exception.AlreadyExistException;
import com.johnbryce.couponsystemphase2.Exception.CanNotChangeExeption;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;

@Service
@Scope("prototype")
public class CompanyService extends ClientService {

	private int companyId;

	public boolean login(String email, String password) throws NotExistException {
		List<Company> companies = companyRepository.findAll();
		for (Company company : companies) {
			if (company.getEmail().equals(email) && company.getPassword().equals(password)) {
				this.companyId = company.getId();
				return true;
			}
		}

		throw new NotExistException("Email or Password");
	}

	public void addCoupon(Coupon coupon) throws AlreadyExistException {
		List<Coupon> companyCoupons = couponRepository.findAll();
		Company company = companyRepository.getOne(companyId);
		for (Coupon coup : companyCoupons) {
			if (coup.getTitle().equals(coupon.getTitle())) {
				throw new AlreadyExistException("Coupon Title");
			}
		}
		couponRepository.save(coupon);
		company.getCoupons().add(coupon);
		companyRepository.saveAndFlush(company);
		
	}

	public void updateCoupon(Coupon coupon) throws CanNotChangeExeption {
		this.couponRepository.saveAndFlush(coupon);

	}

	public void deleteCoupon(int couponId) {
		Company company = companyRepository.getOne(companyId);
		Coupon coupon = couponRepository.getOne(couponId);
		if (coupon.getCompanyId() == companyId) {
			company.getCoupons().remove(coupon);
			companyRepository.saveAndFlush(company);
		}
	}

	
	public List<Coupon> getCompanyCoupons() {
		List<Coupon> coupons = couponRepository.findAll();
		List<Coupon> results = new ArrayList<>();
		for (Coupon coup : coupons) {
			if (coup.getCompanyId() == this.companyId) {
				results.add(coup);
			}
		}
		return results;
	}

	
	public List<Coupon> getCompanyCouponsByCategory(Category category) {
		List<Coupon> coupons = getCompanyCoupons();
		List<Coupon> results = new ArrayList<>();
		for (Coupon coupon : coupons) {
			if (coupon.getCategory().equals(category)) {
				results.add(coupon);
			}
		}
		return results;
	}

	
	public List<Coupon> getCompanyCouponsByPrice(double maxPrice) {
		List<Coupon> coupons = getCompanyCoupons();
		List<Coupon> results = new ArrayList<>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() <= maxPrice) {
				results.add(coupon);
			}
		}
		return results;
	}

	public Company getCompanyDetails() {
		List<Coupon> companysCoupon = getCompanyCoupons();
		Company company = companyRepository.getOne(companyId);
		company.setCoupons(companysCoupon);
		return company;
	}
}
