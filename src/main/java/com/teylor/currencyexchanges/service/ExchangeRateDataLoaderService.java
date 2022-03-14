package com.teylor.currencyexchanges.service;

import com.teylor.currencyexchanges.model.Currency;
import com.teylor.currencyexchanges.model.ExchangeRate;
import com.teylor.currencyexchanges.model.ExchangeType;
import com.teylor.currencyexchanges.utils.DateUtil;
import com.teylor.currencyexchanges.utils.FileReadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ExchangeRateDataLoaderService {

    @Value("${application.data.import.folder}")
    public String EXCHANGE_FILES_FOLDER_PATH ;

    private static final String EXCHANGE_FILE_DATA_SPLITTER = ",";


    private final FileReadUtil fileReadUtil;

    ExchangeRateDataLoaderService(FileReadUtil fileReadUtil){
        this.fileReadUtil = fileReadUtil;
    }

   public List<ExchangeRate> getAllExchangeRatesFromSourceFiles() throws Exception {
        List<ExchangeRate> allExchangeRates = new ArrayList<>();
        for(File file : fileReadUtil.getAllFilesFromResource(EXCHANGE_FILES_FOLDER_PATH)){
            List<ExchangeRate> exchangeRates = getExchangeRatesFromSourceFile(file);
            allExchangeRates.addAll(exchangeRates);
        }
        return allExchangeRates;
    }


    private List<ExchangeRate> getExchangeRatesFromSourceFile(File sourceFile) throws Exception {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile))) {
            String line = bufferedReader.readLine();
            String[] attributes = line.split(",");
            ExchangeType exchangeType = getExchangeType(attributes[1]);
            while((line = bufferedReader.readLine())!=null){
                ExchangeRate exchangeRate = parseExchangeRate(line, exchangeType);
                if(exchangeRate.getRate()!=0.0d){
                    exchangeRates.add(exchangeRate);
                }
            }
        };
        return exchangeRates;
    }

    private ExchangeRate parseExchangeRate(String line, ExchangeType exchangeType) throws ParseException {
        String[] attributes;
        attributes = line.split(EXCHANGE_FILE_DATA_SPLITTER);
        Date exchangeDate = DateUtil.getExchangeDate(attributes[0]);
        double exchangeRate = getExchangeRate(attributes[1]);
        return new ExchangeRate(exchangeType, exchangeDate, exchangeRate);
    }


    private static ExchangeType getExchangeType(String fromTo) throws Exception{
        if(Objects.isNull(fromTo) || fromTo.length()!=6){
            throw new Exception("Undefined Exchange Type");
        }
        Currency from = Currency.valueOf(fromTo.substring(0,3));
        Currency to = Currency.valueOf(fromTo.substring(3));
        return new ExchangeType(from, to);
    }

    private double getExchangeRate(String exchangeRate){
        if(!".".equals(exchangeRate)){
            return Double.parseDouble(exchangeRate);
        }
        return 0.0d;
    }


}
