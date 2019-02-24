import java.util.function.Consumer;

import exception.UnavailableLoanException;
import model.ClosingQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Calculation;
import service.CalculationService;

import static utils.Utils.INVALID_AMOUNT;
import static utils.Utils.isValidRequestedAmount;

public class MainQuote implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MainQuote.class);

    private String csvFile;
    private Integer requestedAmount;
    private Consumer<ClosingQuote> printQuote;

    private MainQuote(String csvFile, Integer requestedAmount) {
        this.csvFile = csvFile;
        this.requestedAmount = requestedAmount;
        printQuote = this::printQuoteWithDetails;
    }

    public static void main(String[] args) {
        new MainQuote(args[0], Integer.valueOf(args[1])).run();
    }

    public void run() {
        if (isValidRequestedAmount(requestedAmount)) {
            ClosingQuote<Calculation> closingQuote = new ClosingQuote<>(requestedAmount, csvFile, new CalculationService());
            printQuote.accept(closingQuote);
        } else {
            try {
                throw new UnavailableLoanException(INVALID_AMOUNT);
            } catch (UnavailableLoanException e) {
                log.error("Error: ", e);
            }
        }
    }

    private void printQuoteWithDetails(ClosingQuote quote) {
        System.out.println(quote);
    }
}
