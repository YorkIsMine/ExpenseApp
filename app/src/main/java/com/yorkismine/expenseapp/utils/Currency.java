package com.yorkismine.expenseapp.utils;

public enum Currency {
    RUB("р"),
    USD ("$"),
    EUR("€"),
    YEN("Y");

    private String currency;
    Currency(String currency) {
        this.currency = currency;
    }
    public String getTypeCurrency(){
        return currency;
    }
}
