package com.teylor.currencyexchanges.service;

import com.teylor.currencyexchanges.model.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExchangeRateDataLoaderServiceTests {

    @Autowired
    ExchangeRateDataLoaderService service;

    @Test
    public void loadExchangeRateData_existingCSVFiles_filesLoadedSuccessfully(){
        try {
            List<ExchangeRate> allExchangeRatesFromSourceFiles = service.getAllExchangeRatesFromSourceFiles();
            assertNotNull(allExchangeRatesFromSourceFiles);
            assertTrue(allExchangeRatesFromSourceFiles.size()>0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
