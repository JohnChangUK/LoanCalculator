package service;

import model.Lender;

import java.math.BigDecimal;
import java.util.List;

public interface Calculation {

    int getMaximumLoanSum(List<Lender> lenders);

    BigDecimal getTotalRateAverage(List<Lender> lenders);

    BigDecimal getPaymentsPerMonth(Integer amount, BigDecimal rate);

    double getTotalPayment(Integer amount, BigDecimal rate);
}
