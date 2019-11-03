package com.yorkismine.expenseapp.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDAO {
    @Insert
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("DELETE FROM expense_table_v7")
    void deleteAllExpenses();

    @Query("SELECT * FROM expense_table_v7")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * FROM expense_table_v7 ORDER BY expense_date asc")
    LiveData<List<Expense>> sortByDate();

    @Query("SELECT * FROM expense_table_v7 ORDER BY expense_title asc")
    LiveData<List<Expense>> sortByName();

    @Query("SELECT * FROM expense_table_v7 ORDER BY expense_sum asc")
    LiveData<List<Expense>> sortBySum();

}
