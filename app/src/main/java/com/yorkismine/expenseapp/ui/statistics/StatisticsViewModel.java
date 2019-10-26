package com.yorkismine.expenseapp.ui.statistics;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yorkismine.expenseapp.MainActivity;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseRepository;

import java.util.List;

public class StatisticsViewModel extends AndroidViewModel {

    private LiveData<List<Expense>> allExpenses;
    private ExpenseRepository repository;

    public StatisticsViewModel(Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        allExpenses = repository.getAllExpenses();
    }

    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }


}