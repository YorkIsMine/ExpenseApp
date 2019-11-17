package com.yorkismine.expenseapp.service;

import com.yorkismine.expenseapp.model.ExchangeRate;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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