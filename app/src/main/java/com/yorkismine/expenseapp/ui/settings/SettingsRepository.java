package com.yorkismine.expenseapp.ui.settings;

import android.content.Context;

public interface SettingsRepository {
    String getCurrencyType();
    void setCurrencyType(Context context,String type);
}
