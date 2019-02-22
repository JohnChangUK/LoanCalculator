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
    private Calculation calculationService;
    private List<Lender> validLenders;

    public ClosingQuote(final Integer requestedAmount, final String csvFile, final T calculationService) {
        this.lenderService = new LenderService<>(csvFile, calculationService);
        this.calculationService = calculationService;
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

    private double getTotalRepayment() {
        return roundUpNumber(2, calculationService.getTotalPayment(
                requestedAmount, calculationService.getTotalRateAverage(validLenders)));
    }

    private double getRate() {
        return roundUpNumber(1,
                calculationService.getTotalRateAverage(validLenders).doubleValue() * 100);
    }

    private double getMonthlyPaymentDouble() {
        return roundUpNumber(2, getMonthlyRepayment().doubleValue());
    }

    private BigDecimal getMonthlyRepayment() {
        return calculationService.getPaymentsPerMonth(requestedAmount, calculationService.getTotalRateAverage(validLenders));
    }

    @Override
    public String toString() {
        return "RequestedAmount: £" + requestedAmount + "\n" +
                "Rate: " + rate + "% " + "\n" +
                "MonthlyRepayment: £" + monthlyRepayment + "\n" +
                "TotalRepayment: £" + totalRepayment;
    }
}
