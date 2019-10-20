package com.yorkismine.expenseapp.recycler;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>{
    private List<Expense> expenses = new ArrayList<>();

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }


    public class ExpenseHolder extends RecyclerView.ViewHolder{

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
