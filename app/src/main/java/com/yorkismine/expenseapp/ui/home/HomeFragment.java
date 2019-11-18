package com.yorkismine.expenseapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.yorkismine.expenseapp.AddExpenseActivity;
import com.yorkismine.expenseapp.AddExpenseBottomDialog;
import com.yorkismine.expenseapp.MainActivity;
import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.adapter.ExpenseAdapter;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.yorkismine.expenseapp.utils.Constants.DEFAULT_VALUE;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CODE_REQUEST;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DATE;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DESC;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_ICON;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_ICON_DESC;
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

        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Add  buttons to sorting by
        final Button btnByDate = root.findViewById(R.id.home_btn_date);
        final Button btnByName = root.findViewById(R.id.home_btn_name);
        final Button btnBySum = root.findViewById(R.id.home_btn_sum);

        //Define dropdown Spinner
        final String[] data = {"Month", "Today", "Week", "Year"};
        mAdapter = homeViewModel.getGridAdapter(root, data);

        Spinner spinner = root.findViewById(R.id.home_spinner);
        spinner.setAdapter(mAdapter);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                homeViewModel.setupSpinner(parent, position, HomeFragment.this, adapter, btnBySum, btnByName, btnByDate);
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