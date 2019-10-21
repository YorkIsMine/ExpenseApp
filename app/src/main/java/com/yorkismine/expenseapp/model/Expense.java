package com.yorkismine.expenseapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table_v1")
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

    @Ignore
    public Expense(){}

    public Expense(String title, String description, String sum) {
        this.title = title;
        this.description = description;
        this.sum = sum;
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
