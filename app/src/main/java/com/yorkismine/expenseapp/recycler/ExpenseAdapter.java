package com.yorkismine.expenseapp.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.model.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY_RUB;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {

    private String sum = "";
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

        //Get date in  milliseconds
        long dateInMillis = Long.parseLong(expense.getDate());
        //Convert date to date format
        Date date = new Date(dateInMillis);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("dd MMM yy");
        holder.tvDate.setText(s.format(date));

        if (expense.getSum() == null) {
            sum = "0 " + EXTRA_CURRENCY_RUB;
            holder.tvSum.setText(sum);
        } else {
            sum = expense.getSum() + " " + EXTRA_CURRENCY_RUB;
            holder.tvSum.setText(sum);
        }

    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    public class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvSum;
        private TextView tvDate;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_tv_title);
            tvDesc = itemView.findViewById(R.id.item_tv_desc);
            tvSum = itemView.findViewById(R.id.item_tv_sum);
            tvDate = itemView.findViewById(R.id.item_tv_date);
        }
    }
}
