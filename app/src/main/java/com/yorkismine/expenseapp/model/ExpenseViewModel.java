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


public class ExpenseViewModel extends AndroidViewModel {
    public final static int SORT_BY_DATE = 0;
    public final static int SORT_BY_NAME = 1;
    public final static int SORT_BY_SUM = 2;
    public final static int SHOW_BY_DAY = 16;
    public final static int SHOW_BY_WEEK = 32;
    public final static int SHOW_BY_MONTH = 64;
    public final static int SHOW_BY_YEAR = 128;

    private ExpenseRepository repository;
    private LiveData<List<Expense>> allExpenses;
    private MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> periodLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        allExpenses = Transformations.switchMap(mutableLiveData, new Function<Integer, LiveData<List<Expense>>>() {
            @Override
            public LiveData<List<Expense>> apply(Integer input) {
                int comparator = input & 3;
                int filter = (input >> 2) << 2;
                final Comparator<Expense> expenseComparator = getComparatorByType(comparator);

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

    public void filter(int type) {
        periodLiveData.setValue(type);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    private Comparator<Expense> getComparatorByType(int type) {
        switch (type) {
            case SORT_BY_DATE:
                return new Comparator<Expense>() {
                    @Override
                    public int compare(Expense expense, Expense t1) {
                        return expense.getDate().compareTo(t1.getDate());
                    }
                };
            case SORT_BY_NAME:
                return new Comparator<Expense>() {
                    @Override
                    public int compare(Expense expense, Expense t1) {
                        return expense.getTitle().compareTo(t1.getTitle());
                    }

                };
            case SORT_BY_SUM:
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

    private Conditions<Expense> getFilterByType(int type) {
        switch (type) {
            case SHOW_BY_DAY:
                return null;
            case SHOW_BY_WEEK:
                return null;
            case SHOW_BY_MONTH:
                return null;
            case SHOW_BY_YEAR:
                return null;
            default:
                return null;
        }
    }

    interface Conditions<E> {
        boolean compare(E a);
    }

}
