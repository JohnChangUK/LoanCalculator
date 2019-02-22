import exception.UnavailableLoanException;
import model.ClosingQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utils.Utils.isValidRequestedAmount;

public class MainQuote {
    private static final Logger log = LoggerFactory.getLogger(MainQuote.class);

    public static void main(String[] args) {
        String data = args[0];
        Integer requestedAmount = Integer.valueOf(args[1]);

        try {
            if (isValidRequestedAmount(requestedAmount)) {
                ClosingQuote closingQuote = new ClosingQuote(requestedAmount, data);
                log.info("Quote: ", closingQuote);
            }
        } catch (UnavailableLoanException e) {
            log.error("Error: ", e);
        }
    }
}
