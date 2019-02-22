package utils;

import exception.UnavailableLoanException;

import java.math.BigDecimal;

public class Utils {
    public static final int MINIMUM_LOAN = 1000;
    public static final int MAXIMUM_LOAN = 15000;
    public static final int LOAN_INCREMENT_STEP = 100;

    public static final String INVALID_AMOUNT = "Requested loan is invalid, please provide an amount between £1,000 and £15,000" +
            "with increments of exactly £100";

    public static final String LOAN_UNAVAILABLE = "Requested loan is unavailable, please try another time";

    public static final String INVALID_INPUT = "Input is invalid, please provide the CSV file as the first argument " +
            "and the loan amount as the second";

    public static double roundUpNumber(int roundUpNumber, double value) {
        BigDecimal decimalValue = new BigDecimal(Double.toString(value));
        return decimalValue.setScale(roundUpNumber, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    public static boolean isValidRequestedAmount(Integer amount) throws UnavailableLoanException {
        if (amount % LOAN_INCREMENT_STEP != 0 || amount < MINIMUM_LOAN || amount > MAXIMUM_LOAN)
            throw new UnavailableLoanException(INVALID_AMOUNT);

        return true;
    }
}
