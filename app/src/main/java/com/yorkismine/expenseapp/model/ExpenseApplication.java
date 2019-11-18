package com.yorkismine.expenseapp.model;

import android.app.Application;

public class ExpenseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ExpenseDatabase.init(getApplicationContext());
    }
}
