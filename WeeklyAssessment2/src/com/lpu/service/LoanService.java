package com.lpu.service;

public class LoanService {

    public boolean isEligible(int age, double salary) {
        return age > 20 && age <= 60 && salary >= 25000;
    }
    
    public double calculateEMI(double loanAmount, int ty) {

        if (loanAmount <= 0) {
            throw new IllegalArgumentException("Loan amount must be > 0");
        }

        if (ty <= 0) {
            throw new IllegalArgumentException("Tenure must be > 0");
        }

        return loanAmount / (ty * 12);
    }

    public String getLoanCategory(int creditScore) {

        if (creditScore >= 750) {
            return "Premium";
        } else if (creditScore >= 600) {
            return "Standard";
        } else {
            return "High Risk";
        }
    }
}
