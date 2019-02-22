import exception.UnavailableLoanException;
import model.ClosingQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Calculation;
import service.CalculationService;

import static utils.Utils.INVALID_AMOUNT;
import static utils.Utils.isValidRequestedAmount;

public class MainQuote {
    private static final Logger log = LoggerFactory.getLogger(MainQuote.class);

    public static void main(String[] args) {

        try {
            String data = args[0];
            Integer requestedAmount = Integer.valueOf(args[1]);
            if (isValidRequestedAmount(requestedAmount)) {
                ClosingQuote<Calculation> closingQuote = new ClosingQuote<>(requestedAmount, data, new CalculationService());
                log.info("Quote: ", closingQuote);
            } else {
                throw new UnavailableLoanException(INVALID_AMOUNT);
            }
        } catch (UnavailableLoanException e) {
            log.error("Error: ", e);
        }
    }
}
