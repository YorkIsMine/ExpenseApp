package com.yorkismine.expenseapp.ui.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.yorkismine.expenseapp.model.Expense
import com.yorkismine.expenseapp.model.ExpenseRepositoryKotl

class StatisticsViewModelKotl(application: Application) : AndroidViewModel(application) {

    internal val allExpenses: LiveData<List<Expense>>
    private val repository: ExpenseRepositoryKotl = ExpenseRepositoryKotl(application)

    init {
        allExpenses = repository.allExpenses
    }


}