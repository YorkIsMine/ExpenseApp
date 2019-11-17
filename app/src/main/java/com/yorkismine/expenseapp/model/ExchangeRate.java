package com.yorkismine.expenseapp.model;

import com.yorkismine.expenseapp.utils.Currency;

public class ExchangeRate {
    private Currency currency;
    private double rate;
    private String name;

    public Currency getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return currency + " : " + rate;
    }
}
