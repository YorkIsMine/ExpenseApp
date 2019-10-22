package com.yorkismine.expenseapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table_v2")
public class Expense {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "expense_title")
    private String title;

    @ColumnInfo(name = "expense_description")
    private String description;

    @ColumnInfo(name = "expense_sum")
    private String sum;

    @ColumnInfo(name = "expense_date")
    private String date;



    @Ignore
    public Expense(){}

    public Expense(String title, String description, String sum, String date) {
        this.title = title;
        this.description = description;
        this.sum = sum;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
