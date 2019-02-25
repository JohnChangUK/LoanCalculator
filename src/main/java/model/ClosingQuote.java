package model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import exception.UnavailableLoanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Calculation;
import service.LenderService;

import static utils.Utils.roundUpNumber;

public class ClosingQuote<T extends Calculation> {

    private static final Logger log = LoggerFactory.getLogger(ClosingQuote.class);

    private Integer requestedAmount;
    private double rate;
    private double monthlyRepayment;
    private double totalRepayment;
    private LenderService lenderService;
    private T calculation;
    private List<Lender> validLenders;

    public ClosingQuote(final Integer requestedAmount, final String csvFile, final T calculation) {
        this.lenderService = new LenderService<>(csvFile, calculation);
        this.calculation = calculation;
        this.requestedAmount = requestedAmount;
        this.validLenders = getValidLenders().orElseGet(Collections::emptyList);
        this.rate = getRate();
        this.monthlyRepayment = getMonthlyPaymentDouble();
        this.totalRepayment = getTotalRepayment();
    }

    private Optional<List<Lender>> getValidLenders() {
        try {
            return Optional.of(lenderService.getLendersFromRequestedAmount(requestedAmount));
        } catch (UnavailableLoanException e) {
            log.error("Error: ", e);
        }
        return Optional.empty();
    }

    double getTotalRepayment() {
        return roundUpNumber(2, calculation.getTotalPayment(
                requestedAmount, calculation.getTotalRateAverage(validLenders)));
    }

    double getRate() {
        return roundUpNumber(1,
                calculation.getTotalRateAverage(validLenders).doubleValue() * 100);
    }

    double getMonthlyPaymentDouble() {
        return roundUpNumber(2, getMonthlyRepayment().doubleValue());
    }

    private BigDecimal getMonthlyRepayment() {
        return calculation.getPaymentsPerMonth(requestedAmount, calculation.getTotalRateAverage(validLenders));
    }

    @Override
    public String toString() {
        return "Requested Amount: £" + requestedAmount + "\n" +
                "Rate: " + rate + "% " + "\n" +
                "Monthly Repayment: £" + monthlyRepayment + "\n" +
                "Total Repayment: £" + totalRepayment;
    }
}
