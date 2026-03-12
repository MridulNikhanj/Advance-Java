package com.lpu.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.lpu.service.LoanService;

public class LoanServiceTest {

    LoanService service = new LoanService();

    @Test
    void testValidEligibility() {
        assertTrue(service.isEligible(25, 30000));
    }

    @Test
    void testInvalidAge() {
        assertFalse(service.isEligible(18, 30000));
    }

    @Test
    void testInvalidSalary() {
        assertFalse(service.isEligible(30, 20000));
    }


    @Test
    void testValidEMI() {
        double emi = service.calculateEMI(120000, 1);
        assertEquals(10000, emi);
    }

    @Test
    void testInvalidLoanAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateEMI(0, 2));
    }

    @Test
    void testInvalidTenure() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateEMI(100000, 0));
    }

    @Test
    void testPremiumCategory() {
        assertEquals("Premium", service.getLoanCategory(800));
    }

    @Test
    void testHighRiskCategory() {
        assertEquals("High Risk", service.getLoanCategory(500));
    }

    @Test
    void testNotNull() {
        assertNotNull(service.getLoanCategory(750));
    }

    @Test
    void testGroupedAssertions() {

        assertAll(
            () -> assertTrue(service.isEligible(30, 40000)),
            () -> assertEquals("Premium", service.getLoanCategory(760)),
            () -> assertEquals(5000, service.calculateEMI(60000, 1))
        );
    }
}
