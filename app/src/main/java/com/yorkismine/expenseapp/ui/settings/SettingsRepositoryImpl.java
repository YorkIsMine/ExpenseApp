package com.yorkismine.expenseapp.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.yorkismine.expenseapp.utils.Currency;

import static com.yorkismine.expenseapp.utils.Constants.PREF_CURRENCY_TYPE;

public class SettingsRepositoryImpl implements SettingsRepository {
    SharedPreferences prefCurrencyType;

    @Override
    public String getCurrencyType() {
        String currencyType = prefCurrencyType.getString(PREF_CURRENCY_TYPE, Currency.USD.toString());
        return currencyType;
    }

    @Override
    public void setCurrencyType(Context context, String type) {
        prefCurrencyType = context.getSharedPreferences(PREF_CURRENCY_TYPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefCurrencyType.edit();
        editor.putString(PREF_CURRENCY_TYPE,type);
        editor.apply();
    }
}
