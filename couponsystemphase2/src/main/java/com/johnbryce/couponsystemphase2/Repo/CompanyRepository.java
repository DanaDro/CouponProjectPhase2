package com.johnbryce.couponsystemphase2.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.couponsystemphase2.Bean.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
