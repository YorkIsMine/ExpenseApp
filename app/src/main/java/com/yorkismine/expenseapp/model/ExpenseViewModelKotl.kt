package com.yorkismine.expenseapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ExpenseViewModelKotl(application: Application) : AndroidViewModel(application) {
    val allExpenses: LiveData<List<ExpenseKotl>>
    private val repository: ExpenseRepositoryKotl

    init {
        repository = ExpenseRepositoryKotl(application)
        allExpenses = repository.allExpenses
    }

    fun insert(expense: ExpenseKotl) {
        repository.insert(expense)
    }

    fun update(expense: ExpenseKotl) {
        repository.update(expense)
    }

    fun delete(expense: ExpenseKotl) {
        repository.delete(expense)
    }

    fun deleteAllExpenses() {
        repository.deleteAllExpenses()
    }
}