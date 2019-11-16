package com.yorkismine.expenseapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ExpenseViewModelKotl(application: Application) : AndroidViewModel(application) {
    val allExpenses: LiveData<List<ExpenseKotl>>
    private val sortByDate: LiveData<List<ExpenseKotl>>
    private val sortByName: LiveData<List<ExpenseKotl>>
    private val sortBySum: LiveData<List<ExpenseKotl>>
    private val repository: ExpenseRepositoryKotl

    init {
        repository = ExpenseRepositoryKotl(application)
        allExpenses = repository.allExpenses
        sortByDate = repository.sortByDate()
        sortByName = repository.sortByName()
        sortBySum = repository.sortBySum()
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

    fun sortByDate(): LiveData<List<ExpenseKotl>> {
        return sortByDate
    }

    fun sortByName(): LiveData<List<ExpenseKotl>> {
        return sortByName
    }

    fun sortBySum(): LiveData<List<ExpenseKotl>> {
        return sortBySum
    }

}