package com.yorkismine.expenseapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yorkismine.expenseapp.AddExpenseActivity;
import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseViewModel;
import com.yorkismine.expenseapp.model.TypeOfExpense;
import com.yorkismine.expenseapp.recycler.ExpenseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CODE_REQUEST;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DATE;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DESC;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_SUM;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_TITLE;

public class HomeFragment extends Fragment {

    private ArrayAdapter mAdapter;
    private HomeViewModel homeViewModel;
    private ExpenseViewModel expenseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        //Define dropdown Spinner
        final String[] data = {"Month", "Day", "Week", "Year", "Sort by sum"};
        mAdapter = new ArrayAdapter<String>(root.getContext(), R.layout.support_simple_spinner_dropdown_item, data) {
            //Set textColor
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
                return tv;
            }
        };

        Spinner spinner = root.findViewById(R.id.home_spinner);
        spinner.setAdapter(mAdapter);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {

                Toast.makeText(getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                expenseViewModel = ViewModelProviders.of(HomeFragment.this).get(ExpenseViewModel.class);
                expenseViewModel.getAllExpenses().observe(HomeFragment.this, new Observer<List<Expense>>() {
                    @Override
                    public void onChanged(final List<Expense> expenses) {
                        List<Expense> byDate = new ArrayList<>();
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
                                case "Day": {
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
                                case "Sort by sum":{
                                    byDate = expenses;
                                    Collections.sort(byDate, new Comparator<Expense>() {
                                        @Override
                                        public int compare(Expense expense, Expense t1) {
                                            Log.e("BLA_!", "" + t1.getSum());
                                            int sum1 = Integer.parseInt(expense.getSum());
                                            int sum2 = Integer.parseInt(t1.getSum());
                                            return sum1 - sum2;
                                        }
                                    });
                                    break;
                                }
                            }
                        }
                        adapter.setExpenses(byDate);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddExpenseActivity.class);
                startActivityForResult(intent, EXTRA_CODE_REQUEST);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EXTRA_CODE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EXTRA_TITLE);
            String desc = data.getStringExtra(EXTRA_DESC);
            String sum = data.getStringExtra(EXTRA_SUM);
            String date = data.getStringExtra(EXTRA_DATE);

            Expense expense = new Expense(title, desc, sum, date);
            expenseViewModel.insert(expense);
        }
    }
}