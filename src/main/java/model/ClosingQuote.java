package model;

import exception.UnavailableLoanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CalculationService;
import service.LenderService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClosingQuote {

    private static final Logger log = LoggerFactory.getLogger(ClosingQuote.class);

    private Integer requestedAmount;
    private double rate;
    private double monthlyRepayment;
    private double totalRepayment;
    private String data;
    private LenderService lenderService;
    private List<Lender> validLenders;

    public ClosingQuote(Integer requestedAmount, String csvFile) {
        this.requestedAmount = requestedAmount;
        this.lenderService = new LenderService(csvFile);
        this.validLenders = getValidLenders().orElseGet(Collections::emptyList);
        this.data = data;
        this.rate = getRate();
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    private Optional<List<Lender>> getValidLenders() {
        try {
            return Optional.of(lenderService.getLendersFromRequestedAmount(requestedAmount));
        } catch (UnavailableLoanException e) {
            log.error("Exception: ", e);
        }
        return Optional.empty();
    }

    private double getTotalRepayment() {
        return roundUpNumber(2, CalculationService.getTotalPayment(
                requestedAmount, CalculationService.getTotalRateAverage(validLenders)));
    }

    private double getRate() {
        return roundUpNumber(1,
                CalculationService.getTotalRateAverage(validLenders).doubleValue() * 100);
    }

    private double roundUpNumber(int roundUpNumber, double value) {
        BigDecimal decimalValue = new BigDecimal(Double.toString(value));
        return decimalValue.setScale(roundUpNumber, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
}
