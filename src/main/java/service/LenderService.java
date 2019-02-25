package service;

import exception.UnavailableLoanException;
import model.Lender;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static utils.Utils.LOAN_UNAVAILABLE;

public class LenderService<T extends Calculation> {

    private static final Logger log = LoggerFactory.getLogger(LenderService.class);

    private List<Lender> allLenders;
    private Integer amountToLend;
    private CSVParser csvParser;
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
        final Path path = Paths.get(Objects.requireNonNull(
                getClass().getClassLoader().getResource(csvFile)).getPath());

        try {
            csvParser = new CSVParser(Files.newBufferedReader(path), CSVFormat.DEFAULT
                    .withIgnoreHeaderCase()
                    .withFirstRecordAsHeader());
        } catch (IOException e) {
            log.error("Error parsing CSV file: ", e);
        }

        allLenders = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            String lender = record.get("Lender");
            BigDecimal rate = new BigDecimal(record.get("Rate"));
            Integer available = Integer.valueOf(record.get("Available"));

            allLenders.add(new Lender(lender, rate, available));
        }

        Collections.sort(allLenders);
        return allLenders;
    }

    public List<Lender> getAllLenders() {
        return allLenders;
    }
}
