package com.teylor.currencyexchanges.controller;

import com.teylor.currencyexchanges.controller.handler.*;
import com.teylor.currencyexchanges.controller.handler.model.InputMap;
import com.teylor.currencyexchanges.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Validated
public class CurrencyExchangeController {

    @Autowired
    RecentExchangeRateInUSDHandler recentExchangeRateInUSDHandler;
    @Autowired
    ExchangeRatesBetweenTwoDatesHandler exchangeRatesBetweenTwoDatesHandler;
    @Autowired
    ExchangeRateAtADateHandler exchangeRateAtADateHandler;
    @Autowired
    SaveExchangeRateHandler saveExchangeRateHandler;



    private static final int QUERY_TYPE_RECENT_EXCHANGE_RATE = 1;
    private static final int QUERY_EXCHANGE_RATES_BETWEEN_TWO_DATES = 2;
    private static final int QUERY_EXCHANGE_RATES_AT_A_DATE = 3;

    @GetMapping("/exchangeRates")
    public ResponseEntity<?> getExchangeRates(
            @RequestParam("query-type") int queryType,
            @RequestParam("currency") String currency,
            @RequestParam("from-date") Optional<String> fromDate,
            @RequestParam("to-date") Optional<String> toDate,
            @RequestParam("at-date") Optional<String> atDate
    )
    {
       return getQueryControllerHandler(queryType).handleRequest(
                InputMap.create()
                        .put(ControllerHandler.INPUT_CURRENCY,currency)
                        .put(ControllerHandler.INPUT_FROM_DATE, fromDate)
                        .put(ControllerHandler.INPUT_TO_DATE, toDate)
                        .put(ControllerHandler.INPUT_AT_DATE, atDate)
        );
    }

    @PutMapping("/exchangeRates")
    public ResponseEntity<?> saveExchangeRate(@RequestBody ExchangeRate exchangeRate)
    {
        return this.saveExchangeRateHandler.handleRequest(InputMap.create().put(SaveExchangeRateHandler.INPUT_EXCHANGE_RATE, exchangeRate));
    }

    private ControllerHandler getQueryControllerHandler(int queryType) {
        switch (queryType){
            case 1:
                return this.recentExchangeRateInUSDHandler;
            case 2:
                return this.exchangeRatesBetweenTwoDatesHandler;
            case 3:
                return this.exchangeRateAtADateHandler;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown query type");
        }
    }


}
