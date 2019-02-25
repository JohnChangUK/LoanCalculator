package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import utils.Utils;
import model.Lender;

import static org.junit.Assert.assertEquals;

public class CalculationServiceTest {
    private List<Lender> validLenders;
    private Calculation calculationService;
    private BigDecimal rate;
    private Integer requestedAmount;
    private double expectedMonthlyPayment;
    private double expectedTotalRepayment;
    private int totalLoanSum;

    @Before
    public void init() {
        validLenders = new ArrayList<>();
        calculationService = new CalculationService();
        rate = new BigDecimal(0.07);
        requestedAmount = 1000;
        expectedMonthlyPayment = 30.88;
        expectedTotalRepayment = 1111.58;
        totalLoanSum = 1640;
        addLenders();
    }

    private void addLenders() {
        validLenders.add(new Lender("Jane", BigDecimal.valueOf(0.069), 480));
        validLenders.add(new Lender("Fred", BigDecimal.valueOf(0.071), 520));
        validLenders.add(new Lender("Bob", BigDecimal.valueOf(0.075), 640));
    }

    @Test
    public void testToGetTheTotalRateAverage() {
        assertEquals(rate.doubleValue(), Utils.roundUpNumber(2, calculationService.getTotalRateAverage(validLenders).doubleValue()), 0);
    }

    @Test
    public void testToGetTheTotalRepaymentCorrelatedWithLoanPeriod() {
        double calculatedTotalRepayment = Utils.roundUpNumber(2, calculationService.getTotalPayment(requestedAmount, rate));
        assertEquals(expectedTotalRepayment, calculatedTotalRepayment, 0);
    }

    @Test
    public void testToGetTheMonthlyPaymentFromRequestAmount() {
        double calculatedMonthlyPayment = Utils.roundUpNumber(
                2, calculationService.getPaymentsPerMonth(requestedAmount, rate).doubleValue());

        assertEquals(expectedMonthlyPayment, calculatedMonthlyPayment, 0);
    }

    @Test
    public void testToGetMaximumLoanSumFromLenders() {
        assertEquals(totalLoanSum, calculationService.getMaximumLoanSum(validLenders));
    }
}