package com.yorkismine.expenseapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.singleton.ExpenseUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY_EUR;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY_RUB;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY_USD;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY_YEN;

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
        holder.tvTypeDesc.setText(expense.getIconDesc());
        if (ExpenseUtil.getType(holder.tvTypeDesc.getText().toString()) != null) {
            holder.ivType.setImageResource(ExpenseUtil.getType(holder.tvTypeDesc.getText().toString()).getImageView());
        }

        //Get date in  milliseconds
        long dateInMillis = expense.getDate();
        //Convert date to date format
        Date date = new Date(dateInMillis);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("dd MMM yyyy");
        holder.tvDate.setText(s.format(date));

        if (expense.getSum() == null) {
            sum = "0 " + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else {
            sum = expense.getSum() + EXTRA_CURRENCY; //ToDo сделать нужную валюту
            String currency = expense.getCurrency();
            double d = Double.valueOf(expense.getSum());
            int sumInt = (int) d;
            checkCurrency(holder, sumInt, currency);
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

    public Expense expenseAt(int pos){
        return expenses.get(pos);
    }

    public class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvSum;
        private TextView tvDate;
        private ImageView ivType;
        private TextView tvTypeDesc;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_tv_title);
            tvDesc = itemView.findViewById(R.id.item_tv_desc);
            tvSum = itemView.findViewById(R.id.item_tv_sum);
            tvDate = itemView.findViewById(R.id.item_tv_date);
            ivType = itemView.findViewById(R.id.expense_item_image_view);
            tvTypeDesc = itemView.findViewById(R.id.type_desc_exp_item);
        }
    }

    private void checkCurrency(ExpenseHolder holder, int s, String currency) {
        if (EXTRA_CURRENCY.equals(currency)) {
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_USD) && currency.equals(EXTRA_CURRENCY_RUB)) {
            s = s / 62;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_USD) && currency.equals(EXTRA_CURRENCY_YEN)) {
            s = s / 10;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_USD) && currency.equals(EXTRA_CURRENCY_EUR)) {
            double d = s * 1.1;
            s = (int) d;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_RUB) && currency.equals(EXTRA_CURRENCY_USD)) {
            s = s * 62;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_RUB) && currency.equals(EXTRA_CURRENCY_YEN)) {
            s = s * 6;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_RUB) && currency.equals(EXTRA_CURRENCY_EUR)) {
            s = s * 71;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_YEN) && currency.equals(EXTRA_CURRENCY_USD)) {
            s = s * 10;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_YEN) && currency.equals(EXTRA_CURRENCY_RUB)) {
            s = s / 6;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_YEN) && currency.equals(EXTRA_CURRENCY_EUR)) {
            s = s / 7;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_EUR) && currency.equals(EXTRA_CURRENCY_USD)) {
            double d = s / 1.1;
            s = (int) d;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_EUR) && currency.equals(EXTRA_CURRENCY_RUB)) {
            s = s / 71;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        } else if (EXTRA_CURRENCY.equals(EXTRA_CURRENCY_EUR) && currency.equals(EXTRA_CURRENCY_YEN)) {
            s = s * 11;
            sum = s + EXTRA_CURRENCY;
            holder.tvSum.setText(sum);
        }
    }

}
