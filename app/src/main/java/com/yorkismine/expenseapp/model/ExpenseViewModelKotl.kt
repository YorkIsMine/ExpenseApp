package com.yorkismine.expenseapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ExpenseViewModelKotl(application: Application) : AndroidViewModel(application) {
    val allExpenses: LiveData<List<Expense>>
    private val repository: ExpenseRepositoryKotl

    init {
        repository = ExpenseRepositoryKotl(application)
        allExpenses = repository.allExpenses
    }

    fun insert(expense: Expense) {
        repository.insert(expense)
    }

    fun update(expense: Expense) {
        repository.update(expense)
    }

    fun delete(expense: Expense) {
        repository.delete(expense)
    }

    fun deleteAllExpenses() {
        repository.deleteAllExpenses()
    }
}