package com.yorkismine.expenseapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.yorkismine.expenseapp.utils.Constants.TYPE_DATE;
import static com.yorkismine.expenseapp.utils.Constants.TYPE_NAME;
import static com.yorkismine.expenseapp.utils.Constants.TYPE_SUM;


public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;
    private LiveData<List<Expense>> allExpenses;
    private MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository();
        allExpenses = Transformations.switchMap(mutableLiveData, new Function<Integer, LiveData<List<Expense>>>() {
            @Override
            public LiveData<List<Expense>> apply(Integer input) {
                Comparator<Expense> expenseComparator = getComparatorByType(input);
                return Transformations.map(repository.getAllExpenses(), new Function<List<Expense>, List<Expense>>() {

                    @Override
                    public List<Expense> apply(List<Expense> input) {
                        Collections.sort(input, expenseComparator);
                        return input;
                    }
                });
            }
        });
    }

    public void insert(Expense expense) {
        repository.insert(expense);
    }

    public void update(Expense expense) {
        repository.update(expense);
    }

    public void delete(Expense expense) {
        repository.delete(expense);
    }

    public void deleteAllExpenses() {
        repository.deleteAllExpenses();
    }


    public void sort(int type) {
        mutableLiveData.setValue(type);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    private Comparator<Expense> getComparatorByType(int type) {
        switch (type) {
            case TYPE_DATE:
                return new Comparator<Expense>() {
                    @Override
                    public int compare(Expense expense, Expense t1) {
                        return expense.getDate().compareTo(t1.getDate());
                    }
                };
            case TYPE_NAME:
                return new Comparator<Expense>() {
                    @Override
                    public int compare(Expense expense, Expense t1) {
                        return expense.getTitle().compareTo(t1.getTitle());
                    }

                };
            case TYPE_SUM:
                return new Comparator<Expense>() {
                    @Override
                    public int compare(Expense expense, Expense t1) {
                        return expense.getSum().compareTo(t1.getSum());
                    }
                };
            default:
                return null;    //ToDo вернуть дефолтный компаратор
        }
    }

}
