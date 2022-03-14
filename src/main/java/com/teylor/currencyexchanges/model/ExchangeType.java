package com.teylor.currencyexchanges.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeType {
    Currency fromCurrency;
    Currency toCurrency;
}
