package com.teylor.currencyexchanges.service;

import com.teylor.currencyexchanges.data.ExchangeRates;
import com.teylor.currencyexchanges.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateUpdateService {

    ExchangeRates exchangeRates;

    ExchangeRateUpdateService(ExchangeRates exchangeRates){
        this.exchangeRates = exchangeRates;
    }

    public void updateExchangeRate(ExchangeRate exchangeRate){
        List<ExchangeRate> rates = exchangeRates.getExchangeRates()
                .stream()
                .filter(f -> f.getType().getToCurrency()== exchangeRate.getType().getToCurrency()
                && f.getType().getFromCurrency()==exchangeRate.getType().getFromCurrency()
                && f.getDate().equals(exchangeRate.getDate())
                ).collect(Collectors.toList());
        if(!rates.isEmpty()){
            for(ExchangeRate rate : rates){
                rate.setRate(exchangeRate.getRate());
            }
        }else{
            exchangeRates.insert(exchangeRate);
        }
    }

}
