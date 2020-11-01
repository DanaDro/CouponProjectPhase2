package com.johnbryce.couponsystemphase2.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.couponsystemphase2.Bean.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

}
