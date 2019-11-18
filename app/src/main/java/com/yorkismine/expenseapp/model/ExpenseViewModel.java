package com.yorkismine.expenseapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


//Он уже ж не нужен
public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository();
    }

    public void insert(Expense expense){
        repository.insert(expense);
    }

    public void update(Expense expense){
        repository.update(expense);
    }

    public void delete(Expense expense){
        repository.delete(expense);
    }

    public void deleteAllExpenses(){
        repository.deleteAllExpenses();
    }

}
