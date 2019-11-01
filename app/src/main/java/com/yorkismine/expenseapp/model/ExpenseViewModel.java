package com.yorkismine.expenseapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private LiveData<List<Expense>> allExpenses;
    private LiveData<List<Expense>> sortByDate;
    private LiveData<List<Expense>> sortByName;
    private LiveData<List<Expense>> sortBySum;
    private ExpenseRepository repository;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        allExpenses = repository.getAllExpenses();
        sortByDate = repository.sortByDate();
        sortByName = repository.sortByName();
        sortBySum = repository.sortBySum();
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

    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }
    public LiveData<List<Expense>> sortByDate(){
        return sortByDate;
    }
    public LiveData<List<Expense>> sortByName(){
        return sortByName;
    }
    public LiveData<List<Expense>> sortBySum(){
        return sortBySum;
    }

}
