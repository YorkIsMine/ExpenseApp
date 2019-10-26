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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
    private BarChart barChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(StatisticsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        dashboardViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                setupPieChart(root, expenses);
                setupBarChart(root, expenses);
            }
        });
        return root;
    }

    private void setupBarChart(View view, List<Expense> expenses) {
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < expenses.size(); i++) {
            float y = Float.parseFloat(expenses.get(i).getSum());
            String x = expenses.get(i).getTitle();
            barEntries.add(new BarEntry(i, y));
        }
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.calcMinMax();
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(15f);
        BarData barData = new BarData(dataSet);
        barChart = view.findViewById(R.id.bar_chart);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.animateY(500);
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
        String[] colors = {"#FFF000", "#CCCFFF"};
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setSliceSpace(1f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setDrawValues(false);
        dataSet.setSelectionShift(10f);

        PieData pieData = new PieData(dataSet);
        // Get the chart
        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.setHoleRadius(20f);
        pieChart.setMinAngleForSlices(30f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.animateY(500);
    }
}