package com.yorkismine.expenseapp.model

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_v1")
class ExpenseKotl(){
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    @ColumnInfo(name = "expense_title")
    private var title: String? = null

    @ColumnInfo(name = "expense_description")
    private var description: String? = null

    @ColumnInfo(name = "expense_sum")
    private var sum: Int? = null

    @ColumnInfo(name = "expense_currency")
    private var currency: String? = null

    @ColumnInfo(name = "expense_date")
    private var date: Long? = null

    @ColumnInfo(name = "expense_icon")
    private var icon: Int = 0

    @ColumnInfo(name = "expense_icon_desc")
    private var iconDesc: String? = null
}