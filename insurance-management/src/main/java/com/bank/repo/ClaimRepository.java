package com.bank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.model.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>{
	List<Claim> findByPolicyNumber(String policyNumber);

}
