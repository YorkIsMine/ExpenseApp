package com.yorkismine.expenseapp.ui.statistics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel dashboardViewModel;
    private PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(StatisticsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        dashboardViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                setupPieChart(root, expenses);
            }
        });
        return root;
    }

    // TODO set min size of item on pie
    private void setupPieChart(View view, List<Expense> expenses) {
        //Add items as entries
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < expenses.size(); i++) {
            float y = Float.parseFloat(expenses.get(i).getSum());
            String x = expenses.get(i).getTitle();
            pieEntries.add(new PieEntry(y, x));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "Expenses stats");
        dataSet.setSliceSpace(2.0f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(18f);

        PieData pieData = new PieData(dataSet);
        // Get the chart
        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.setHoleRadius(24f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expenses");
        pieChart.setCenterTextSize(18f);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}