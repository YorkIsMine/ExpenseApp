package com.yorkismine.expenseapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table_v6")
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

    @ColumnInfo(name = "expense_currency")
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @ColumnInfo(name = "expense_date")
    private String date;

    @ColumnInfo(name = "expense_icon")
    private int icon;

    @ColumnInfo(name = "expense_icon_desc")
    private String iconDesc;

    @Ignore
    public Expense() {
    }

    public Expense(String title, String description, String sum, String currency, String date, int icon, String iconDesc) {
        this.title = title;
        this.description = description;
        this.sum = sum;
        this.currency = currency;
        this.date = date;
        this.icon = icon;
        this.iconDesc = iconDesc;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIconDesc() {
        return iconDesc;
    }

    public void setIconDesc(String iconDesc) {
        this.iconDesc = iconDesc;
    }
}
