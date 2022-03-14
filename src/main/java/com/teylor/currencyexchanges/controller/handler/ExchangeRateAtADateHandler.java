package com.teylor.currencyexchanges.controller.handler;

import com.teylor.currencyexchanges.controller.handler.model.InputMap;
import com.teylor.currencyexchanges.controller.handler.model.InputValidationResult;
import com.teylor.currencyexchanges.model.Currency;
import com.teylor.currencyexchanges.model.ExchangeRate;
import com.teylor.currencyexchanges.service.ExchangeRateQueryService;
import com.teylor.currencyexchanges.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ExchangeRateAtADateHandler extends ControllerHandler{

    Logger logger = LoggerFactory.getLogger(ExchangeRateAtADateHandler.class);

    ExchangeRateQueryService exchangeRateQueryService;

    ExchangeRateAtADateHandler(ExchangeRateQueryService exchangeRateQueryService){
        this.exchangeRateQueryService = exchangeRateQueryService;
    }

    @Override
    public InputValidationResult validateInputParams(InputMap inputParams) throws ResponseStatusException {

        InputValidationResult currencyValidationResult = validateCurrency(inputParams);
        if(!currencyValidationResult.isValidationSuccess()){
            return currencyValidationResult;
        }
        try{
            Date atDate = DateUtil.getExchangeDate(((Optional<String>) inputParams.get(INPUT_AT_DATE)).get());
        }catch(NoSuchElementException noSuchElementException){
            return InputValidationResult.getInputValidationFailResult("at-date is not present");
        } catch (ParseException e){
            return InputValidationResult.getInputValidationFailResult("Invalid at-date");
        }
        return InputValidationResult.getInputValidationSuccessResult();
    }

    @Override
    public ResponseEntity<?> handleRequest(InputMap inputMap) throws ResponseStatusException {
        handleValidationResult(inputMap);
        Date atDate;
        try {
            atDate = DateUtil.getExchangeDate(((Optional<String>) inputMap.get(INPUT_AT_DATE)).get());
        } catch (NoSuchElementException | ParseException e) {
            logger.error("Unexpected date parse error.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date");
        }
        List<ExchangeRate> exchangeRates = this.exchangeRateQueryService.getExchangeRatesAtDate(atDate);
        if(exchangeRates!=null){
            return ResponseEntity.ok().body(exchangeRates);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exchange rate not found");
        }
    }

}
