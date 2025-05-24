package com.bank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.model.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long>{
	List<Billing> findByPolicyNumber(String policyNumber);
    boolean existsByPolicyNumberAndPaymentStatus(String policyNumber, String paymentStatus);

}
