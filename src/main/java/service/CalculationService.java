package service;

import model.Lender;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class CalculationService implements Calculation {

    private static final Integer LOAN_REPAYMENT_DURATION = 36;

    @Override
    public int getMaximumLoanSum(final List<Lender> lenders) {
        int loanTotal = 0;
        for (Lender lender : lenders) {
            loanTotal += lender.getAvailable();
        }

        return loanTotal;
    }

    @Override
    public BigDecimal getTotalRateAverage(final List<Lender> lenders) {
        BigDecimal rateTotal = BigDecimal.ZERO;
        for (Lender lender : lenders) {
            rateTotal = rateTotal.add(lender.getRate());
        }

        return rateTotal.divide(BigDecimal.valueOf(lenders.size()), MathContext.DECIMAL64);
    }

    /**
     * Algorithm for calculating monthly payments
     * n = 36 (3 years times 12 monthly payments per year)
     * i = .005 (6 percent annually expressed as .06, divided by 12 monthly payments per year—learn how to convert percentages to decimal format)
     * D = 166.7916 ({[(1+.005)^36] - 1} / [.005(1+.005)^36])
     * P = A / D = 100,000 / 166.7916 = 599.55
     */
    @Override
    public BigDecimal getPaymentsPerMonth(final Integer amount, final BigDecimal rate) {
        BigDecimal monthlyInterestRate = rate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL64);

        BigDecimal discountNumerator = monthlyInterestRate.add(BigDecimal.ONE)
                .pow(LOAN_REPAYMENT_DURATION, MathContext.DECIMAL64)
                .subtract(BigDecimal.ONE);

        BigDecimal discountDenominator = monthlyInterestRate.multiply(BigDecimal.ONE
                .add(monthlyInterestRate)
                .pow(LOAN_REPAYMENT_DURATION, MathContext.DECIMAL64));

        BigDecimal discountFactor = discountNumerator.divide(discountDenominator, MathContext.DECIMAL64);

        return BigDecimal.valueOf(amount).divide(discountFactor, MathContext.DECIMAL64);
    }

    @Override
    public double getTotalPayment(final Integer amount, final BigDecimal rate) {
        return getPaymentsPerMonth(amount, rate)
                .multiply(BigDecimal.valueOf(LOAN_REPAYMENT_DURATION))
                .doubleValue();
    }
}
