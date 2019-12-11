package com.yorkismine.expenseapp.ui.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SettingsViewModel extends AndroidViewModel {
    private SettingsRepository repository;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        repository = SettingsRepository.getInstance(application);
    }

    public void saveCurrency(String currency, String value){
        repository.saveCurrency(currency, value);
    }


}