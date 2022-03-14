# Spring Boot Currency Exchange Rate Rest API Application 

This is a sample Java / Maven / Spring Boot (version 2.6.4) application for a demo currency exchange rates service. 

## How to Run

* Clone this repository
* Make sure you are using JDK 11 

```
        java -jar target/currencyexchanges-0.0.1-SNAPSHOT.jar 
or
        mvn spring-boot:run
```

## About the Service

## GetLatestExchangeRate

```

http://localhost:8080/api/v1/exchangeRates?query-type=1&currency=CHF

Response: 

{
    "type": {
        "fromCurrency": "CHF",
        "toCurrency": "USD"
    },
    "date": "2021-01-29",
    "rate": 0.8905
}

```


## GetExchangeRatesBetweenTwoDates

```

http://localhost:8080/api/v1/exchangeRates?query-type=2&currency=CHF&from-date=2001-01-30&to-date=2023-12-30

Response : 

[
    {
        "type": {
            "fromCurrency": "CHF",
            "toCurrency": "USD"
        },
        "date": "2016-01-29",
        "rate": 1.0226
    },
    {
        "type": {
            "fromCurrency": "CHF",
            "toCurrency": "USD"
        },
        "date": "2016-02-01",
        "rate": 1.0202
    }
]

```

## GetExchangeRatesAtDate

```

http://localhost:8080/api/v1/exchangeRates?query-type=3&currency=CHF&at-date=2016-02-08

Response : 
[
    {
        "type": {
            "fromCurrency": "CHF",
            "toCurrency": "USD"
        },
        "date": "2016-02-08",
        "rate": 0.9885
    },
    {
        "type": {
            "fromCurrency": "CNY",
            "toCurrency": "USD"
        },
        "date": "2016-02-08",
        "rate": 6.571
    },
    {
        "type": {
            "fromCurrency": "JPY",
            "toCurrency": "USD"
        },
        "date": "2016-02-08",
        "rate": 115.7
    }
]

```

## SaveExchangeRate

```


PUT http://localhost:8080/api/v1/exchangeRates
Accept: application/json
Content-Type: application/json

{
    "type": {
        "fromCurrency": "TRY",
        "toCurrency": "USD"
    },
    "date": "2016-02-08",
    "rate": 0.342
}

Response :
    
    Success
```

# Questions and Comments: cunver@hotmail.com

