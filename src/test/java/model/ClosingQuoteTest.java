package model;

import org.junit.Before;
import org.junit.Test;
import service.Calculation;
import service.CalculationService;

import static org.junit.Assert.assertEquals;

public class ClosingQuoteTest {

    ClosingQuote<Calculation> quote;

    @Before
    public void init() {
        quote = new ClosingQuote<>(1000, "marketdata.csv", new CalculationService());
    }

    @Test
    public void testTheCorrectOutputWithGivenInputOf1000() {
        assertEquals(7.0, quote.getRate(), 0);
        assertEquals(30.88, quote.getMonthlyPaymentDouble(), 0);
        assertEquals(1111.58, quote.getTotalRepayment(), 0);
    }
}