package com.yorkismine.expenseapp.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<List<String>> mCurrencies;

    public LiveData<List<String>> getCurrencies() {
        return mCurrencies;
    }
}