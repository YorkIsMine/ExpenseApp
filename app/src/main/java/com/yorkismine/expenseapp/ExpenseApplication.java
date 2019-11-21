package com.yorkismine.expenseapp;

import android.app.Application;

import com.yorkismine.expenseapp.model.ExpenseDatabase;

public class ExpenseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ExpenseDatabase.init(getApplicationContext());
    }
}
