package com.yorkismine.expenseapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.adapter.ExpenseAdapter;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends ViewModel {

    public void setupSpinner(final AdapterView<?> parent, final int position,
                              Fragment fragment, final ExpenseAdapter adapter,
                              final Button btnBySum, final Button btnByName, final Button btnByDate){

        ExpenseViewModel expenseViewModel = ViewModelProviders.of(fragment).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(fragment, new Observer<List<Expense>>() {
            @Override
            public void onChanged(final List<Expense> expenses) {
                final List<Expense> byDate = new ArrayList<>();
                Date currDate = new Date();

                for (int i = 0; i < expenses.size(); i++) {

                    String currentDate = parent.getItemAtPosition(position).toString();
                    String fromItem = expenses.get(i).getDate();

                    switch (currentDate) {
                        case "Month": {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat monthS = new SimpleDateFormat("MM");
                            Date dateFrom = new Date(Long.parseLong(fromItem));
                            if (monthS.format(dateFrom).equals(monthS.format(currDate))) {
                                byDate.add(expenses.get(i));
                            }
                            break;
                        }
                        case "Year": {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat yearS = new SimpleDateFormat("yy");
                            Date dateFrom = new Date(Long.parseLong(fromItem));
                            if (yearS.format(dateFrom).equals(yearS.format(currDate))) {
                                byDate.add(expenses.get(i));
                            }
                            break;
                        }
                        case "Today": {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dayS = new SimpleDateFormat("dd MM yy");
                            Date dateFrom = new Date(Long.parseLong(fromItem));
                            if (dayS.format(dateFrom).equals(dayS.format(currDate))) {
                                byDate.add(expenses.get(i));
                            }
                            break;
                        }
                        case "Week": {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat weekS = new SimpleDateFormat("w");
                            Date dateFrom = new Date(Long.parseLong(fromItem));
                            if (weekS.format(dateFrom).equals(weekS.format(currDate))) {
                                byDate.add(expenses.get(i));
                            }
                            break;
                        }
                    }
                }
                adapter.setExpenses(byDate);

                // Add sorting by request
                btnByDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.setExpenses(sortByDate(byDate));
                    }
                });
                btnByName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.setExpenses(sortByName(byDate));
                    }
                });
                btnBySum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.setExpenses(sortBySum(byDate));
                    }
                });
            }
        });
    }

    public void setupLogicOfSwipe(final ExpenseViewModel expenseViewModel, final Context context, final ExpenseAdapter adapter, RecyclerView recyclerView){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                expenseViewModel.delete(adapter.expenseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(context, "Expense was deleted", Toast.LENGTH_SHORT)
                        .show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    public ArrayAdapter<String> getGridAdapter(View root, String[] data){
        return new ArrayAdapter<String>(root.getContext(), R.layout.support_simple_spinner_dropdown_item, data) {
            //Set textColor
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
                return tv;
            }
        };
    }

    private List<Expense> sortByDate(List<Expense> byDate) {
        Collections.sort(byDate, new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return byDate;
    }
    private List<Expense> sortByName(List<Expense> byDate) {
        Collections.sort(byDate, new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        return byDate;
    }
    private List<Expense> sortBySum(List<Expense> byDate) {
        Collections.sort(byDate, new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return o1.getSum().compareTo(o2.getSum());
            }
        });
        return byDate;
    }
}