package com.yorkismine.expenseapp.model

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ExpenseRepositoryKotl(context: Context) {
    val allExpenses: LiveData<List<ExpenseKotl>>
    private val sortByDate: LiveData<List<ExpenseKotl>>
    private val sortByName: LiveData<List<ExpenseKotl>>
    private val sortBySum: LiveData<List<ExpenseKotl>>
    private val expenseDAOKotl: ExpenseDAOKotl

    init {
        val database = ExpenseDatabaseKotl.getInstance(context)
        expenseDAOKotl = database?.expenseDAOKotl()!!
        allExpenses = expenseDAOKotl.allExpenses
        sortByDate = expenseDAOKotl.sortByDate()
        sortByName = expenseDAOKotl.sortByName()
        sortBySum = expenseDAOKotl.sortBySum()
    }

    fun insert(expense: ExpenseKotl) {
        InsertExpenseAsyncTask(expenseDAOKotl).execute(expense)
    }

    fun update(expense: ExpenseKotl) {
        UpdateExpenseAsyncTask(expenseDAOKotl).execute(expense)
    }

    fun delete(expense: ExpenseKotl) {
        DeleteExpenseAsyncTask(expenseDAOKotl).execute(expense)
    }

    fun deleteAllExpenses() {
        DeleteAllExpensesAsyncTask(expenseDAOKotl).execute()
    }

    fun sortByDate(): LiveData<List<ExpenseKotl>> {
        return sortByDate
    }

    fun sortByName(): LiveData<List<ExpenseKotl>> {
        return sortByName
    }

    fun sortBySum(): LiveData<List<ExpenseKotl>> {
        return sortBySum
    }


    private class InsertExpenseAsyncTask(private val expenseDAO: ExpenseDAOKotl) : AsyncTask<ExpenseKotl, Void, Void>() {

        override fun doInBackground(vararg expenses: ExpenseKotl): Void? {
            expenseDAO.insert(expenses[0])
            return null
        }
    }

    private class UpdateExpenseAsyncTask(private val expenseDAO: ExpenseDAOKotl) : AsyncTask<ExpenseKotl, Void, Void>() {

        override fun doInBackground(vararg expenses: ExpenseKotl): Void? {
            expenseDAO.update(expenses[0])
            return null
        }
    }

    private class DeleteExpenseAsyncTask(private val expenseDAO: ExpenseDAOKotl) : AsyncTask<ExpenseKotl, Void, Void>() {

        override fun doInBackground(vararg expenses: ExpenseKotl): Void? {
            expenseDAO.delete(expenses[0])
            return null
        }
    }

    private class DeleteAllExpensesAsyncTask(private val expenseDAO: ExpenseDAOKotl) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            expenseDAO.deleteAllExpenses()
            return null
        }
    }


}