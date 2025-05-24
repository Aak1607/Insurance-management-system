package com.bank.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.model.Policy;
import com.bank.service.PolicyService;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private static final Logger logger = LoggerFactory.getLogger(PolicyController.class);

    @Autowired
    private PolicyService policyService;

    @PostMapping
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
        logger.info("Received request to create a new policy");
        Policy createdPolicy = policyService.createPolicy(policy);
        return ResponseEntity.ok(createdPolicy);
    }

    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {
        logger.info("Fetching all policies");
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        logger.info("Fetching policy with ID: {}", id);
        return policyService.getPolicyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Policy not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @RequestBody Policy policy) {
        logger.info("Updating policy with ID: {}", id);
        try {
            Policy updatedPolicy = policyService.updatePolicy(id, policy);
            return ResponseEntity.ok(updatedPolicy);
        } catch (Exception e) {
            logger.error("Error updating policy: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        logger.info("Deleting policy with ID: {}", id);
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}

