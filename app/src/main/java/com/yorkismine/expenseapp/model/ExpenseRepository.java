package com.yorkismine.expenseapp.model;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseRepository {
    private LiveData<List<Expense>> allExpenses;
    private ExpenseDAO expenseDAO;

    public ExpenseRepository() {
        ExpenseDatabase database = ExpenseDatabase.getInstance();
        expenseDAO = database.expenseDAO();
        allExpenses = expenseDAO.getAllExpenses();
    }

    public void insert(Expense expense) {
        new InsertExpenseAsyncTask(expenseDAO).execute(expense);
    }

    public void update(Expense expense) {
        new UpdateExpenseAsyncTask(expenseDAO).execute(expense);
    }

    public void delete(Expense expense) {
        new DeleteExpenseAsyncTask(expenseDAO).execute(expense);
    }

    public void deleteAllExpenses() {
        new DeleteAllExpensesAsyncTask(expenseDAO).execute();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
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
