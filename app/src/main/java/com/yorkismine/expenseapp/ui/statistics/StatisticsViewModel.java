package com.yorkismine.expenseapp.ui.statistics;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseRepository;

import java.util.List;

public class StatisticsViewModel extends AndroidViewModel {

    private LiveData<List<Expense>> allExpenses;
    private ExpenseRepository repository;

    public StatisticsViewModel(Application application) {
        super(application);
        repository = new ExpenseRepository();
        allExpenses = repository.getAllExpenses();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }


}