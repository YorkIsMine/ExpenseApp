package com.yorkismine.expenseapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
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
import com.yorkismine.expenseapp.MainActivity;
import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseViewModel;
import com.yorkismine.expenseapp.recycler.ExpenseAdapter;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CODE_REQUEST;
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
        String[] data = {"Month", "Day", "Week", "Year"};
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);

        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                adapter.setExpenses(expenses);
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

            Expense expense = new Expense(title, desc, sum);
            expenseViewModel.insert(expense);
        }
    }
}