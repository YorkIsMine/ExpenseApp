package com.yorkismine.expenseapp.service;

import com.yorkismine.expenseapp.model.ExchangeRate;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ExchangeRateSrvTest {

    @Test
    public void load(){
        ExchangeRateSrv srv = new ExchangeRateSrv();
        List<ExchangeRate> rates = srv.load();
        for(ExchangeRate rate : rates){
            System.out.println(rate);
        }
        Assert.assertEquals(3, rates.size());
    }
}