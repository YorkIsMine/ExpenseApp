package com.yorkismine.expenseapp.service;

import com.yorkismine.expenseapp.model.ExchangeRate;
import com.yorkismine.expenseapp.service.ExchangeRateSrv;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ExchangeRateSrvTest {

    @Test
    public void load() throws IOException {
        ExchangeRateSrv serv = new ExchangeRateSrv();
        List<ExchangeRate> rates = serv.load();
        for(ExchangeRate rate : rates){
            System.out.println(rate);
        }
        Assert.assertEquals(3, rates.size());
    }
}