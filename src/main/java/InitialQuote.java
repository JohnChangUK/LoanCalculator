import exception.UnavailableLoanException;
import model.ClosingQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

public class InitialQuote {

    private static final Logger log = LoggerFactory.getLogger(InitialQuote.class);

    private static final int MINIMUM_LOAN = 1000;
    private static final int MAXIMUM_LOAN = 15000;
    private static final int LOAN_INCREMENT_STEP = 100;

    public static void main(String[] args) {
        String data = args[0];
        Integer requestedAmount = Integer.valueOf(args[1]);

        try {
            if (isValidRequestedAmount(requestedAmount)) {
                ClosingQuote closingQuote = new ClosingQuote(requestedAmount, data);
                log.info("Final Quote: ", closingQuote);
            }
        } catch (UnavailableLoanException e) {
            log.error("Exception: ", e);
        }
    }

    private static boolean isValidRequestedAmount(Integer amount) throws UnavailableLoanException {
        if (amount % LOAN_INCREMENT_STEP != 0 || amount < MINIMUM_LOAN || amount > MAXIMUM_LOAN)
            throw new UnavailableLoanException(Utils.INVALID_AMOUNT);

        return true;
    }
}
