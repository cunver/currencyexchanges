package com.teylor.currencyexchanges.controller.handler;

import com.teylor.currencyexchanges.controller.handler.model.InputMap;
import com.teylor.currencyexchanges.controller.handler.model.InputValidationResult;
import com.teylor.currencyexchanges.model.Currency;
import com.teylor.currencyexchanges.model.ExchangeRate;
import com.teylor.currencyexchanges.service.ExchangeRateQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RecentExchangeRateInUSDHandler extends ControllerHandler{

    ExchangeRateQueryService exchangeRateQueryService;

    RecentExchangeRateInUSDHandler(ExchangeRateQueryService exchangeRateQueryService){
        this.exchangeRateQueryService = exchangeRateQueryService;
    }

    @Override
    public InputValidationResult validateInputParams(InputMap inputParams) throws ResponseStatusException {
        try{
            Currency.valueOf((String) inputParams.get(INPUT_CURRENCY));
        }catch (IllegalArgumentException e ){
            return InputValidationResult.getInputValidationFailResult("Invalid currency");
        }
        return InputValidationResult.getInputValidationSuccessResult();
    }

    @Override
    public ResponseEntity<?> handleRequest(InputMap inputMap) throws ResponseStatusException {
        handleValidationResult(inputMap);
        Optional<ExchangeRate> exchangeRateOptional = this.exchangeRateQueryService.getLatestExchangeRateInUSD(Currency.valueOf((String) inputMap.get(INPUT_CURRENCY)));
        if(exchangeRateOptional.isPresent()){
            return ResponseEntity.ok().body(exchangeRateOptional.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found");
        }
    }
}
