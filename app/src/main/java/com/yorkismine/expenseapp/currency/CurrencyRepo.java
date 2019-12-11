package com.yorkismine.expenseapp.currency;

import androidx.lifecycle.LiveData;

import com.yorkismine.expenseapp.currency.Currency;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyRepo {
    @GET("daily_json.js")
    LiveData<Currency> getCurrency();
}
