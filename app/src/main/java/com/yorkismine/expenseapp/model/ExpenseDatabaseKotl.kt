package com.yorkismine.expenseapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExpenseKotl::class], version = 1, exportSchema = false)
abstract class ExpenseDatabaseKotl : RoomDatabase() {

    abstract fun expenseDAOKotl(): ExpenseDAOKotl

    companion object {
        private var instance: ExpenseDatabaseKotl? = null

        @Synchronized
        fun getInstance(context: Context): ExpenseDatabaseKotl? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        ExpenseDatabaseKotl::class.java, "expenses_v1").build()
            }

            return instance
        }
    }
}