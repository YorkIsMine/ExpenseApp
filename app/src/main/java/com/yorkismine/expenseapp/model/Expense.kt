package com.yorkismine.expenseapp.model

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_v1")
data class Expense(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "expense_title")
    var title: String? = null,

    @ColumnInfo(name = "expense_description")
    var description: String? = null,

    @ColumnInfo(name = "expense_sum")
    var sum: Int? = null,

    @ColumnInfo(name = "expense_currency")
    var currency: String? = null,

    @ColumnInfo(name = "expense_date")
    var date: Long? = null,

    @ColumnInfo(name = "expense_icon")
    var icon: Int = 0,

    @ColumnInfo(name = "expense_icon_desc")
    var iconDesc: String? = null
)