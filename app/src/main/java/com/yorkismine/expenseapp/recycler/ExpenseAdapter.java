package com.yorkismine.expenseapp.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>{
    private List<Expense> expenses = new ArrayList<>();

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.tvTitle.setText(expense.getTitle());
        holder.tvDesc.setText(expense.getDescription());
        if (expense.getSum() == null) {
            holder.tvSum.setText("0");
        } else {
            holder.tvSum.setText(expense.getSum());
        }

    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenses(List<Expense> expenses){
        this.expenses = expenses;
        notifyDataSetChanged(); //ToDo optimize memory
    }

    public class ExpenseHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvSum;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_tv_title);
            tvDesc = itemView.findViewById(R.id.item_tv_desc);
            tvSum = itemView.findViewById(R.id.item_tv_sum);
        }
    }
}
