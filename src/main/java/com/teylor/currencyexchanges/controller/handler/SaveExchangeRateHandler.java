package com.teylor.currencyexchanges.controller.handler;

import com.teylor.currencyexchanges.controller.handler.model.InputMap;
import com.teylor.currencyexchanges.controller.handler.model.InputValidationResult;
import com.teylor.currencyexchanges.model.ExchangeRate;
import com.teylor.currencyexchanges.service.ExchangeRateUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SaveExchangeRateHandler extends ControllerHandler{

    ExchangeRateUpdateService exchangeRateUpdateService;
    SaveExchangeRateHandler(ExchangeRateUpdateService exchangeRateUpdateService){
        this.exchangeRateUpdateService = exchangeRateUpdateService;
    }

    @Override
    public InputValidationResult validateInputParams(InputMap inputMap) {
        return null;
    }

    @Override
    public ResponseEntity<?> handleRequest(InputMap inputMap) throws ResponseStatusException {
        ExchangeRate exchangeRate = (ExchangeRate) inputMap.get(INPUT_EXCHANGE_RATE);
        this.exchangeRateUpdateService.updateExchangeRate(exchangeRate);
        return ResponseEntity.ok().body("Success");
    }

}
