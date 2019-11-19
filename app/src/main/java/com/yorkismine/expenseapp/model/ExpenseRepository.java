package com.yorkismine.expenseapp.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ExpenseRepository {

    void insert(Expense expense);

    void update(Expense expense);

    void delete(Expense expense);

    void deleteAllExpenses();

    LiveData<List<Expense>> getAllExpenses();

    LiveData<List<Expense>> sortByDate();

    LiveData<List<Expense>> sortByName();

    LiveData<List<Expense>> sortBySum();
}
