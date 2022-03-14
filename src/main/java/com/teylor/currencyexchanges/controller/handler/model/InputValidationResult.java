package com.teylor.currencyexchanges.controller.handler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputValidationResult {
    private boolean isValidationSuccess;
    private String validationMessage;

    public static InputValidationResult getInputValidationSuccessResult(){
        return new InputValidationResult(true, "");
    }

    public static InputValidationResult getInputValidationFailResult(String message){
        return new InputValidationResult(false, message);
    }

}
