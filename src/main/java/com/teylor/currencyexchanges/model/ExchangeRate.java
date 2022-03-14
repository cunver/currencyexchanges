package com.teylor.currencyexchanges.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExchangeRate {

    private ExchangeType type;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    private double rate;

}
