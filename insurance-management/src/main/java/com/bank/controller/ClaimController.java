package com.bank.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.Claim;
import com.bank.service.ClaimService;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
	
    private static final Logger logger = LoggerFactory.getLogger(ClaimController.class);
	
	@Autowired
	private ClaimService claimService;
	
	public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }
	
	@PostMapping
	public ResponseEntity<Claim> createClaim(@RequestBody Claim claim){
        logger.info("POST /api/claims - Creating claim");
        return ResponseEntity.ok(claimService.createClaim(claim));
	}
	
	@GetMapping
	public ResponseEntity<List<Claim>> getAllClaims(){
        logger.info("GET /api/claims - Fetching all claims");
        return ResponseEntity.ok(claimService.getAllClaims());
	}
	
	
	// Get claims by policy number
	@GetMapping("/policy/{policyNumber}")
	public ResponseEntity<List<Claim>> getClaimsByPolicy(@PathVariable String policyNumber) {
	    return ResponseEntity.ok(claimService.getClaimsByPolicyNumber(policyNumber));
	}
	
	// Get claim by claim ID
	@GetMapping("/id/{id}")
	public ResponseEntity<Claim> getClaimByID(@PathVariable Long id) {
	    return ResponseEntity.ok(claimService.getClaimById(id));
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable Long id, @RequestBody Claim claim) {
        logger.info("PUT /api/claims/{} - Updating claim", id);
        return ResponseEntity.ok(claimService.updateClaim(id, claim));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        logger.info("DELETE /api/claims/{} - Deleting claim", id);
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }

}
