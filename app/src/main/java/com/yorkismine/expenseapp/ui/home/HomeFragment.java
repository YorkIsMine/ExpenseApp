package com.yorkismine.expenseapp.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yorkismine.expenseapp.AddExpenseBottomDialog;
import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.adapter.ExpenseAdapter;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseViewModel;
import com.yorkismine.expenseapp.model.FilterUtils;
import com.yorkismine.expenseapp.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    private ArrayAdapter mAdapter;
    private ExpenseViewModel expenseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        expenseViewModel = ViewModelProviders.of(HomeFragment.this).get(ExpenseViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Add  buttons to sorting by
        final Button btnByDate = root.findViewById(R.id.home_btn_date);
        final Button btnByName = root.findViewById(R.id.home_btn_name);
        final Button btnBySum = root.findViewById(R.id.home_btn_sum);

        //Define dropdown Spinner
        final String[] data = {"Month", "Today", "Week", "Year"};
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

        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseViewModel.sort(Constants.SORT_BY_DATE);
            }
        });
        btnByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseViewModel.sort(Constants.SORT_BY_NAME);
            }
        });
        btnBySum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseViewModel.sort(Constants.SORT_BY_SUM);
            }
        });

        expenseViewModel.getAllExpenses().observe(HomeFragment.this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                adapter.setExpenses(expenses);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                String currentDate = parent.getItemAtPosition(position).toString();

                switch (currentDate) {
                    case "Month": {
                        expenseViewModel.filter(Constants.SHOW_BY_MONTH);
                        break;
                    }
                    case "Year": {
                        expenseViewModel.filter(Constants.SHOW_BY_YEAR);
                        break;
                    }
                    case "Today": {
                        expenseViewModel.filter(Constants.SHOW_BY_DAY);
                        break;
                    }
                    case "Week": {
                        expenseViewModel.filter(Constants.SHOW_BY_WEEK);
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExpenseBottomDialog dialog = new AddExpenseBottomDialog();
                dialog.show(getActivity().getSupportFragmentManager(), "DIALOG_BOTTOM_ADD_EXPENSE_TAG");
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                expenseViewModel.delete(adapter.expenseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Expense was deleted", Toast.LENGTH_SHORT)
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        return root;
    }


}