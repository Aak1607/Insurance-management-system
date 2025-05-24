package com.bank.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.exception.ResourceNotFoundException;
import com.bank.model.Billing;
import com.bank.model.Policy;
import com.bank.repo.BillingRepository;
import com.bank.repo.PolicyRepository;

@Service
public class BillingService {
	
    private static final Logger logger = LoggerFactory.getLogger(BillingService.class);
	
	@Autowired
	private BillingRepository billingRepository;
	
	@Autowired
    private final PolicyRepository policyRepository;

	public BillingService(BillingRepository billingRepository, PolicyRepository policyRepository) {
		super();
		this.billingRepository = billingRepository;
		this.policyRepository = policyRepository;
	}
	
	
	public Billing payBill(Long billId) {
	    Billing bill = billingRepository.findById(billId)
	        .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + billId));

	    if ("Paid".equalsIgnoreCase(bill.getPaymentStatus())) {
	        throw new IllegalStateException("Bill is already paid.");
	    }

	    bill.setPaymentStatus("Paid");
	    bill.setPaymentDate(LocalDate.now()); // add this field in your Billing entity if not already
	    return billingRepository.save(bill);
	}
	
	
	public String getBillStatusByPolicy(String policyNumber) {
	    List<Billing> bills = billingRepository.findByPolicyNumber(policyNumber);

	    if (bills.isEmpty()) {
	        return "No Bill Found";
	    }

	    boolean hasPaid = bills.stream().anyMatch(b -> "Paid".equalsIgnoreCase(b.getPaymentStatus()));
	    boolean hasUnpaid = bills.stream().anyMatch(b -> "Unpaid".equalsIgnoreCase(b.getPaymentStatus()));

	    if (hasPaid) return "Paid";
	    if (hasUnpaid) return "Unpaid";

	    return "Unknown";
	}


	
    
	public Billing generateBill(Billing billing) {
        logger.info("Attempting to generate bill for policy number: {}", billing.getPolicyNumber());

        // 1. Validate policy
        Policy policy = policyRepository.findAll().stream()
            .filter(p -> p.getPolicyNumber().equals(billing.getPolicyNumber()))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        if (!"Active".equalsIgnoreCase(policy.getStatus())) {
            throw new IllegalStateException("Cannot generate bill. Policy is not active.");
        }

        // 2. Save billing info
        billing.setPaymentStatus("Unpaid"); // Default to unpaid
        billing.setBillNumber("BILL-" + System.currentTimeMillis());

        return billingRepository.save(billing);
    }
	
	public List<Billing> getBillsByPolicy(String policyNumber) {
        return billingRepository.findByPolicyNumber(policyNumber);
    }



}
