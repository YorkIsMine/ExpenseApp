package com.yorkismine.expenseapp.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseRepositoryImpl implements ExpenseRepository {
    private LiveData<List<Expense>> allExpenses;
    private LiveData<List<Expense>> sortByDate;
    private LiveData<List<Expense>> sortByName;
    private LiveData<List<Expense>> sortBySum;
    private ExpenseDAO expenseDAO;
    private ExpenseRepositoryImpl instance;

    public ExpenseRepositoryImpl(Context context) {
        ExpenseDatabase database = ExpenseDatabase.getInstance(context);
        expenseDAO = database.expenseDAO();
        allExpenses = expenseDAO.getAllExpenses();
        sortByDate = expenseDAO.sortByDate();
        sortByName = expenseDAO.sortByName();
        sortBySum = expenseDAO.sortBySum();
    }

    @Override
    public void insert(Expense expense){
        new InsertExpenseAsyncTask(expenseDAO).execute(expense);
    }

    @Override
    public void update(Expense expense){
        new UpdateExpenseAsyncTask(expenseDAO).execute(expense);
    }

    @Override
    public void delete(Expense expense){
        new DeleteExpenseAsyncTask(expenseDAO).execute(expense);
    }

    @Override
    public void deleteAllExpenses(){
        new DeleteAllExpensesAsyncTask(expenseDAO).execute();
    }

    @Override
    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }

    @Override
    public LiveData<List<Expense>> sortByDate(){
        return sortByDate;
    }
    @Override
    public LiveData<List<Expense>> sortByName(){
        return sortByName;
    }
    @Override
    public LiveData<List<Expense>> sortBySum(){
        return sortBySum;
    }




    private static class InsertExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDAO expenseDAO;

        public InsertExpenseAsyncTask(ExpenseDAO expenseDAO) {
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDAO.insert(expenses[0]);
            return null;
        }
    }

    private static class UpdateExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDAO expenseDAO;

        public UpdateExpenseAsyncTask(ExpenseDAO expenseDAO) {
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDAO.update(expenses[0]);
            return null;
        }
    }

    private static class DeleteExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDAO expenseDAO;

        public DeleteExpenseAsyncTask(ExpenseDAO expenseDAO) {
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDAO.delete(expenses[0]);
            return null;
        }
    }

    private static class DeleteAllExpensesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExpenseDAO expenseDAO;

        public DeleteAllExpensesAsyncTask(ExpenseDAO expenseDAO) {
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDAO.deleteAllExpenses();
            return null;
        }
    }




}
