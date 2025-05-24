package com.bank.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.exception.ResourceNotFoundException;
import com.bank.model.Claim;
import com.bank.model.Policy;
import com.bank.repo.BillingRepository;
import com.bank.repo.ClaimRepository;
import com.bank.repo.PolicyRepository;

import java.util.List;

@Service
public class ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);

	
	@Autowired
	private ClaimRepository claimRepository;
	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private BillingRepository billingRepository;
	@Autowired
	private BillingService billingService;


	
	
	public ClaimService(ClaimRepository claimRepository,
            PolicyRepository policyRepository,
            BillingRepository billingRepository) {
            this.claimRepository = claimRepository;
            this.policyRepository = policyRepository;
            this.billingRepository = billingRepository;
            }


	
	public Claim createClaim(Claim claim) {
	    // 1. Validate Policy exists
		Policy policy = policyRepository.findByPolicyNumber(claim.getPolicyNumber())
			    .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));


	    // 2. Check if policy is active
	    if (!"Active".equalsIgnoreCase(policy.getStatus())) {
	        throw new IllegalStateException("Cannot file claim. Policy is not active.");
	    }

	    // 3. Check if bill is paid for this policy
	    String billStatus = billingService.getBillStatusByPolicy(claim.getPolicyNumber());
	    if (!"Paid".equalsIgnoreCase(billStatus)) {
	        throw new IllegalStateException("Cannot file claim. Bill is not paid for this policy.");
	    }

	    // 4. Set claim status & save
	    claim.setStatus("Pending"); // Default status
	    return claimRepository.save(claim);
	}

	
	
    public List<Claim> getAllClaims() {
        logger.info("Fetching all claims");
        return claimRepository.findAll();
    }
    
    public List<Claim> getClaimsByPolicyNumber(String policyNumber) {
        return claimRepository.findByPolicyNumber(policyNumber);
    }


    public Claim getClaimById(Long id) {
        logger.info("Fetching claim with ID: {}", id);
        return claimRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Claim with ID {} not found", id);
                    return new ResourceNotFoundException("Claim with ID " + id + " not found");
                });
    }

    public Claim updateClaim(Long id, Claim claimDetails) {
        logger.info("Updating claim with ID: {}", id);
        Claim claim = getClaimById(id);
        claim.setClaimNumber(claimDetails.getClaimNumber());
        claim.setPolicyNumber(claimDetails.getPolicyNumber());
        claim.setClaimType(claimDetails.getClaimType());
        claim.setClaimAmount(claimDetails.getClaimAmount());
        claim.setStatus(claimDetails.getStatus());
        return claimRepository.save(claim);
    }

    public void deleteClaim(Long id) {
        logger.info("Deleting claim with ID: {}", id);
        claimRepository.deleteById(id);
    }
}

