package com.yorkismine.expenseapp.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.yorkismine.expenseapp.utils.FilterUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ExpenseViewModel extends AndroidViewModel {


    private ExpenseRepository repository;
    private LiveData<List<Expense>> allExpenses;
    private MutableLiveData<Integer> sortLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> periodLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepositoryImpl(application);
        ConditionLiveData conditionLiveData = new ConditionLiveData(sortLiveData, periodLiveData);
        allExpenses = Transformations.switchMap(conditionLiveData, new Function<Integer, LiveData<List<Expense>>>() {
            @Override
            public LiveData<List<Expense>> apply(Integer input) {
                int comparator = input & 3;
                int filter = (input >> 2) << 2;
                final Comparator<Expense> expenseComparator = FilterUtils.INSTANCE.getComparatorByType(comparator);
                final Conditions<Expense> expenseConditions = FilterUtils.INSTANCE.getConditionByType(filter);
                return Transformations.map(repository.getAllExpenses(), new Function<List<Expense>, List<Expense>>() {

                    @Override
                    public List<Expense> apply(List<Expense> input) {
                        List<Expense> buf = new ArrayList<>();
                        for(Expense e: input){
                            if(expenseConditions.check(e)){
                                buf.add(e);
                            }
                        }
                        Collections.sort(buf, expenseComparator);
                        return buf;
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
        sortLiveData.setValue(type);
    }

    public void filter(int type) {
        periodLiveData.setValue(type);
    }


    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }


}
