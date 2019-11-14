package com.yorkismine.expenseapp.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDAOKotl {

    @get:Query("SELECT * FROM expenses_v1")
    val allExpenses: LiveData<List<ExpenseKotl>>

    @Insert
    fun insert(expense: ExpenseKotl)

    @Update
    fun update(expense: ExpenseKotl)

    @Delete
    fun delete(expense: ExpenseKotl)

    @Query("DELETE FROM expenses_v1")
    fun deleteAllExpenses()

    @Query("SELECT * FROM expenses_v1 ORDER BY expense_date asc")
    fun sortByDate(): LiveData<List<ExpenseKotl>>

    @Query("SELECT * FROM expenses_v1 ORDER BY expense_title asc")
    fun sortByName(): LiveData<List<ExpenseKotl>>

    @Query("SELECT * FROM expenses_v1 ORDER BY expense_sum asc")
    fun sortBySum(): LiveData<List<ExpenseKotl>>

}