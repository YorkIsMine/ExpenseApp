package com.yorkismine.expenseapp.service;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class ExchangeRateTest {

    @Test
    public void load() throws IOException {
        ExchangeRate serv = new ExchangeRate();
        InputStream is = (InputStream) serv.load();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (reader.ready()){
            System.out.println(reader.readLine());
        }
    }
}