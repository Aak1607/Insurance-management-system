package com.bank.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.Billing;
import com.bank.service.BillingService;

@RestController
@RequestMapping("/api/billings")
public class BillingController {
	

	private final BillingService billingService;

    public BillingController(BillingService billingService) {
		super();
		this.billingService = billingService;
	}
    
    @PutMapping("/pay/{billId}")
    public ResponseEntity<Billing> payBill(@PathVariable Long billId) {
        return ResponseEntity.ok(billingService.payBill(billId));
    }

    @GetMapping("/status/{policyNumber}")
    public ResponseEntity<String> getBillStatus(@PathVariable String policyNumber) {
        return ResponseEntity.ok(billingService.getBillStatusByPolicy(policyNumber));
    }


	@PostMapping
    public ResponseEntity<Billing> generateBill(@RequestBody Billing billing) {
        return ResponseEntity.ok(billingService.generateBill(billing));
    }

    @GetMapping("/{policyNumber}")
    public ResponseEntity<List<Billing>> getBillsByPolicy(@PathVariable String policyNumber) {
        return ResponseEntity.ok(billingService.getBillsByPolicy(policyNumber));
    }

}
