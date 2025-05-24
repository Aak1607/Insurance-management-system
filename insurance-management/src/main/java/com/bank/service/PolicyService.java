package com.bank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.exception.ResourceNotFoundException;
import com.bank.model.Policy;
import com.bank.repo.PolicyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

	private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public Policy createPolicy(Policy policy) {
        logger.info("Creating policy: {}", policy);
        return policyRepository.save(policy);
    }

    public List<Policy> getAllPolicies() {
        logger.info("Fetching all policies");
        return policyRepository.findAll();
    }

    public Optional<Policy> getPolicyById(Long id) {
        logger.info("Fetching policy with ID: {}", id);
        return policyRepository.findById(id);
    }

    public Policy updatePolicy(Long id, Policy policyDetails) {
        logger.info("Updating policy with ID: {}", id);
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Policy with ID {} not found", id);
                    return new ResourceNotFoundException("Policy with ID " + id + " not found");
                });

        policy.setPolicyNumber(policyDetails.getPolicyNumber());
        policy.setPolicyHolderName(policyDetails.getPolicyHolderName());
        policy.setPolicyType(policyDetails.getPolicyType());
        policy.setPremiumAmount(policyDetails.getPremiumAmount());

        return policyRepository.save(policy);
    }

    public void deletePolicy(Long id) {
        logger.info("Deleting policy with ID: {}", id);
        policyRepository.deleteById(id);
    }
}
