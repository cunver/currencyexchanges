package com.teylor.currencyexchanges.controller.handler;

import com.teylor.currencyexchanges.controller.handler.model.InputMap;
import com.teylor.currencyexchanges.controller.handler.model.InputValidationResult;
import com.teylor.currencyexchanges.model.Currency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public abstract class ControllerHandler {

    public static final String INPUT_CURRENCY = "INPUT_CURRENCY";
    public static final String INPUT_FROM_DATE = "INPUT_FROM_DATE";
    public static final String INPUT_TO_DATE = "INPUT_TO_DATE";
    public static final String INPUT_AT_DATE = "INPUT_AT_DATE";
    public static final String INPUT_EXCHANGE_RATE = "INPUT_EXCHANGE_RATE";


    public abstract ResponseEntity<?> handleRequest(InputMap inputMap) throws ResponseStatusException;

    public abstract InputValidationResult validateInputParams(InputMap inputMap);


    public void handleValidationResult(InputMap inputMap) throws ResponseStatusException{
        InputValidationResult inputValidationResult = validateInputParams(inputMap);
        if(!inputValidationResult.isValidationSuccess()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, inputValidationResult.getValidationMessage());
        }

    }

    public InputValidationResult validateCurrency(InputMap inputMap) throws ResponseStatusException{
        try{
            Currency.valueOf((String) inputMap.get(INPUT_CURRENCY));
        }catch (IllegalArgumentException e ){
            return InputValidationResult.getInputValidationFailResult("Invalid currency");
        }
        return InputValidationResult.getInputValidationSuccessResult();
    }


}
