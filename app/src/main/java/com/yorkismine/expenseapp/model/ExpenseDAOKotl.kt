package com.yorkismine.expenseapp.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDAOKotl {

    @get:Query("SELECT * FROM expenses_v1")
    val allExpenses: LiveData<List<Expense>>

    @Insert
    fun insert(expense: Expense)

    @Update
    fun update(expense: Expense)

    @Delete
    fun delete(expense: Expense)

    @Query("DELETE FROM expenses_v1")
    fun deleteAllExpenses()

    @Query("SELECT * FROM expenses_v1 ORDER BY expense_date asc")
    fun sortByDate(): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses_v1 ORDER BY expense_title asc")
    fun sortByName(): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses_v1 ORDER BY expense_sum asc")
    fun sortBySum(): LiveData<List<Expense>>

}