package com.teylor.currencyexchanges.service;

import com.teylor.currencyexchanges.data.ExchangeRates;
import com.teylor.currencyexchanges.model.Currency;
import com.teylor.currencyexchanges.model.ExchangeRate;
import com.teylor.currencyexchanges.model.ExchangeType;
import com.teylor.currencyexchanges.utils.DateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.text.ParseException;
import java.util.*;


@ExtendWith(MockitoExtension.class)
public class ExchangeRateQueryServiceTests {

    @Mock
    ExchangeRates exchangeRates;

    @InjectMocks
    ExchangeRateQueryService exchangeRateQueryService;

    private static List<ExchangeRate> exchangeRateList;
    private static Date datePrev, dateAfter;
    private static double rateCFHPrev = 0.9999d , rateCHFAfter = 0.9998d;

    @BeforeAll
    public static void prepareExchangeRateList(){
        List<ExchangeRate> list = new ArrayList<>();
        ExchangeRate rate1, rate2, rate3, rate4;
        try {
            datePrev = DateUtil.getExchangeDate("2008-01-01");
            dateAfter = DateUtil.getExchangeDate("2008-01-02");
            rate1 = new ExchangeRate(new ExchangeType(Currency.CHF, Currency.USD), datePrev,rateCFHPrev);
            rate2 = new ExchangeRate(new ExchangeType(Currency.JPY, Currency.USD), datePrev,0.8888d);
            rate3 = new ExchangeRate(new ExchangeType(Currency.CHF, Currency.USD), dateAfter,rateCHFAfter);
            rate4 = new ExchangeRate(new ExchangeType(Currency.JPY, Currency.USD), dateAfter,0.8887d);
            list.addAll(Arrays.asList(rate1,rate2,rate3,rate4));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        exchangeRateList = list;
    }

   @Test
   public void getLatestExchangeRateInUSD_hasData_returnExchangeRate(){
       when(exchangeRates.getExchangeRates()).thenReturn(exchangeRateList);
       Optional<ExchangeRate> latestExchangeRateInUSD = exchangeRateQueryService.getLatestExchangeRateInUSD(Currency.CHF);
       assertTrue(latestExchangeRateInUSD.isPresent());
       assertEquals(latestExchangeRateInUSD.get().getDate(), dateAfter);
       assertEquals(latestExchangeRateInUSD.get().getRate(), rateCHFAfter);
   }

    @Test
    public void getLatestExchangeRateInUSD_hasNoData_returnNoRecord(){
        when(exchangeRates.getExchangeRates()).thenReturn(new ArrayList<ExchangeRate>());
        Optional<ExchangeRate> latestExchangeRateInUSD = exchangeRateQueryService.getLatestExchangeRateInUSD(Currency.CHF);
        assertFalse(latestExchangeRateInUSD.isPresent());
    }

    @Test
    public void getExchangeRatesBetweenTwoDates_hasData_ReturnExchangeRates(){
        when(exchangeRates.getExchangeRates()).thenReturn(exchangeRateList);
        Date oneDayBeforePrevDate = new Date(datePrev.getTime() - (1000 * 60 * 60 * 24));
        Date oneDayAfterAfterDate = new Date(dateAfter.getTime() + (1000 * 60 * 60 * 24));
        List<ExchangeRate> exchangeRatesBetweenTwoDates = exchangeRateQueryService.getExchangeRatesBetweenTwoDates(Currency.CHF, oneDayBeforePrevDate , oneDayAfterAfterDate);
        assertTrue(exchangeRatesBetweenTwoDates != null && exchangeRatesBetweenTwoDates.size() == 2);
        for(ExchangeRate rate : exchangeRatesBetweenTwoDates){
            assertEquals(rate.getType().getFromCurrency(), Currency.CHF);
            assertTrue(rate.getDate().after(oneDayBeforePrevDate));
            assertTrue(rate.getDate().before(oneDayAfterAfterDate));
        }
    }

    @Test
    public void getExchangeRatesAtDate_hasData_returnsRecords() throws ParseException {
        when(exchangeRates.getExchangeRates()).thenReturn(exchangeRateList);
        List<ExchangeRate> exchangeRatesAtDateList = exchangeRateQueryService.getExchangeRatesAtDate(dateAfter);
        assertTrue(exchangeRatesAtDateList != null && exchangeRatesAtDateList.size() == 2);
        assertNotEquals(exchangeRatesAtDateList.get(0).getType().getFromCurrency(), exchangeRatesAtDateList.get(1).getType().getFromCurrency());
        assertSame(exchangeRatesAtDateList.get(0).getDate(),exchangeRatesAtDateList.get(1).getDate());
    }

    @Test
    public void getExchangeRatesAtDate_hasNoData_returnsNoRecord() throws ParseException {
        Mockito.when(exchangeRates.getExchangeRates()).thenReturn(new ArrayList<ExchangeRate>());
        List<ExchangeRate> exchangeRateList = exchangeRateQueryService.getExchangeRatesAtDate(dateAfter );
        assertEquals(0, exchangeRateList.size());
    }
}
