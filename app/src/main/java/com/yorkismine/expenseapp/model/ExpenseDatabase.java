package com.yorkismine.expenseapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class}, version = 1, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase {
    private static ExpenseDatabase instance;

    public abstract ExpenseDAO expenseDAO();

    public static synchronized ExpenseDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExpenseDatabase.class, "expense_table_v3").build();
        }

        return instance;
    }
}
