package com.teylor.currencyexchanges.service;

import com.teylor.currencyexchanges.data.ExchangeRates;
import com.teylor.currencyexchanges.model.Currency;
import com.teylor.currencyexchanges.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeRateQueryService {

    ExchangeRates exchangeRates;

    ExchangeRateQueryService(ExchangeRates exchangeRates){
        this.exchangeRates = exchangeRates;
    }

    public Optional<ExchangeRate> getLatestExchangeRateInUSD(Currency currency) {
        return exchangeRates.getExchangeRates().stream()
                .filter(exchangeRate -> exchangeRate.getType().getToCurrency()== Currency.USD && exchangeRate.getType().getFromCurrency()==currency)
                .max((t1,t2)-> t1.getDate().compareTo(t2.getDate()));
    }

    public List<ExchangeRate> getExchangeRatesBetweenTwoDates(Currency currency, Date fromDate, Date toDate) {
        return exchangeRates.getExchangeRates().stream().
                filter(exchangeRate -> exchangeRate.getType().getToCurrency()==Currency.USD
                        && exchangeRate.getType().getFromCurrency()==currency
                        && exchangeRate.getDate().after(fromDate)
                        && exchangeRate.getDate().before(toDate)
                ).collect(Collectors.toList());
    }

    public List<ExchangeRate> getExchangeRatesAtDate(Date atDate) {
        return exchangeRates.getExchangeRates().stream().filter(exchangeRate -> exchangeRate.getDate().equals(atDate)
        ).collect(Collectors.toList());
    }

}
