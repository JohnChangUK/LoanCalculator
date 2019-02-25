package service;

import java.util.Arrays;
import java.util.List;

import exception.UnavailableLoanException;
import model.Lender;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LenderServiceTest {

    private LenderService<Calculation> lenderService;
    private List<Lender> allLenders;
    private List<Lender> expectedValidLenders;
    private Lender jane;
    private Lender fred;
    private int requestedAmount;
    private int invalidAmount;

    @Before
    public void init() {
        lenderService = new LenderService<>("src/main/resources/marketdata.csv", new CalculationService());
        allLenders = lenderService.getAllLenders();
        jane = allLenders.get(0);
        fred = allLenders.get(1);
        expectedValidLenders = Arrays.asList(jane, fred);
        requestedAmount = 1000;
        invalidAmount = 1000000;
    }

    @Test(expected = UnavailableLoanException.class)
    public void testToSeeIfExceptionIsThrownWhenGivenInvalidRequestAmount() throws UnavailableLoanException {
        lenderService.getLendersFromRequestedAmount(invalidAmount);
    }

    @Test
    public void testLenderServiceToReturnThePersonWithLowestRateAndSufficientFunds() throws UnavailableLoanException {
        List<Lender> calculatedValidLenders = lenderService.getLendersFromRequestedAmount(requestedAmount);
        assertEquals(expectedValidLenders, calculatedValidLenders);
    }
}