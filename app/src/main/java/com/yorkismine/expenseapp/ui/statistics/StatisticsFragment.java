package com.yorkismine.expenseapp.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseViewModel;
import com.yorkismine.expenseapp.recycler.ExpenseAdapter;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel dashboardViewModel;
    private ExpenseViewModel viewModel;
    private PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                ViewModelProviders.of(this).get(StatisticsViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_statistics, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        final View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = root.findViewById(R.id.expense_diagram);

        viewModel =
                ViewModelProviders.of(this).get(ExpenseViewModel.class);
        viewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                drawChart(root, expenses);
            }
        });

        return root;
    }

    private void drawChart(View view, List<Expense> expenses){
        pieChart = view.findViewById(R.id.expense_diagram);
        pieChart.setUsePercentValues(true);
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        for(Expense expense : expenses) {
            float sum = (float) Integer.parseInt(expense.getSum());
            yvalues.add(new PieEntry(sum, expense.getTitle(), sum));
        }

            PieDataSet pieDataSet = new PieDataSet(yvalues, "res");
            PieData pieData = new PieData(pieDataSet);

            pieData.setValueFormatter(new PercentFormatter());
            pieChart.setData(pieData);
            Description description = new Description();
            description.setText("BLA");
            pieChart.setDescription(description);
            pieChart.setDrawHoleEnabled(true);
            pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

    }
}