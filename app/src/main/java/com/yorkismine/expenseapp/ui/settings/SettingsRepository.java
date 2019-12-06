package com.yorkismine.expenseapp.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.yorkismine.expenseapp.currency.Currency;

public class SettingsRepository {
    private static SharedPreferences sharedPreferences;
    private static SettingsRepository instance;

    public SettingsRepository(Context context){
       sharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
    }

    public void saveCurrency(String currency, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(currency, value);
        editor.apply();

    }

    public static SettingsRepository getInstance(Context context) {
        if (instance == null) instance = new SettingsRepository(context);
        return instance;
    }

    public void loadCurrency(){

    }
}
