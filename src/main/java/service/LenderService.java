package service;

import exception.UnavailableLoanException;
import model.Lender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.Utils.LOAN_UNAVAILABLE;

public class LenderService<T extends Calculation> {

    private static final Logger log = LoggerFactory.getLogger(LenderService.class);

    private List<Lender> allLenders;
    private Integer amountToLend;
    private Calculation calculationService;

    public LenderService(final String csvFile, final T calculation) {
        this.allLenders = getLendersFromCsv(csvFile);
        this.calculationService = calculation;
        this.amountToLend = calculationService.getMaximumLoanSum(allLenders);
    }

    public List<Lender> getLendersFromRequestedAmount(final Integer requestedAmount) throws UnavailableLoanException {
        Integer amount = requestedAmount;
        List<Lender> validLenders = new ArrayList<>();

        if (amount > amountToLend) {
            throw new UnavailableLoanException(LOAN_UNAVAILABLE);
        }

        for (Lender lender : allLenders) {
            if (amount <= 0) {
                break;
            } else if (lender.getAvailable() >= amount) {
                validLenders.add(lender);
                amount = 0;
            } else {
                amount -= lender.getAvailable();
                validLenders.add(lender);
            }
        }

        return validLenders;
    }

    private List<Lender> getLendersFromCsv(String csvFile) {
        allLenders = new ArrayList<>();

        try {
            allLenders = CSVReader.loadDataFromCSVFile(csvFile);
        } catch (IOException e) {
            log.error("Error parsing CSV: ", e);
        }

        Collections.sort(allLenders);
        return allLenders;
    }

    public List<Lender> getAllLenders() {
        return allLenders;
    }
}
