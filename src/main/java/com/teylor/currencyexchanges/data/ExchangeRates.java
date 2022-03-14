package com.teylor.currencyexchanges.data;

import com.teylor.currencyexchanges.model.ExchangeRate;
import com.teylor.currencyexchanges.service.ExchangeRateDataLoaderService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExchangeRates {


    private static List<ExchangeRate> EXCHANGE_RATES = new ArrayList<>();

    private final ExchangeRateDataLoaderService exchangeRateDataLoaderService;

    ExchangeRates(ExchangeRateDataLoaderService exchangeRateDataLoaderService){
        this.exchangeRateDataLoaderService = exchangeRateDataLoaderService;
    }

    public static boolean isExchangeRatesLoaded(){
        return EXCHANGE_RATES != null && !EXCHANGE_RATES.isEmpty();
    }

    public List<ExchangeRate> getExchangeRates(){
        if(!isExchangeRatesLoaded()){
            try {
                loadExchangeRateData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return EXCHANGE_RATES;
    }

    public void loadExchangeRateData() throws Exception {
        List<ExchangeRate> exchangeRateList = this.exchangeRateDataLoaderService.getAllExchangeRatesFromSourceFiles();
        if(!exchangeRateList.isEmpty()){
            synchronized(this){
                ExchangeRates.EXCHANGE_RATES = exchangeRateList;
            }
        }
    }

    public void insert(ExchangeRate exchangeRate) {
        synchronized (this){
            EXCHANGE_RATES.add(exchangeRate);
        }
    }
}
