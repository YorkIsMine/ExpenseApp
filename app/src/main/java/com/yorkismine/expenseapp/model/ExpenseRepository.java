package com.yorkismine.expenseapp.model;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ExpenseRepository {
    public void insert(Expense expense);
    public void update(Expense expense);
    public void delete(Expense expense);
    public void deleteAllExpenses();

    public LiveData<List<Expense>> getAllExpenses();

    public LiveData<List<Expense>> sortByDate();

    public LiveData<List<Expense>> sortByName();

    public LiveData<List<Expense>> sortBySum();
}
