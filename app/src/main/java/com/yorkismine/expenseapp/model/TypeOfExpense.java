package com.yorkismine.expenseapp.model;

public class TypeOfExpense {
    private String type;
    private int imageView;

    public TypeOfExpense(String type, int imageView) {
        this.type = type;
        this.imageView = imageView;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
