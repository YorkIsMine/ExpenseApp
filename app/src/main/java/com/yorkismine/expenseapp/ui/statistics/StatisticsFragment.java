package com.yorkismine.expenseapp.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
//        for (int i = 0; i < expenses.size(); i++) {
//            float y = Float.parseFloat(expenses.get(i).getSum());
//            String x = expenses.get(i).getTitle();
//            barEntries.add(new BarEntry(i, y));
//        }
        float sum = 0;
        float animal = 0;
        float home = 0;
        float games = 0;
        float food = 0;
        float waste = 0;
        float party = 0;
        float friends = 0;

        for (int i = 0; i < expenses.size(); i++) {
            float values = Float.parseFloat(expenses.get(i).getSum());
            switch (expenses.get(i).getIconDesc()) {
                case "Animal":
                    animal = animal + values;
                    break;
                case "Home":
                    home = home + values;
                    break;
                case "Games":
                    games = games + values;
                    break;
                case "Food":
                    food = food + values;
                    break;
                case "Waste":
                    waste = waste + values;
                    break;
                case "Party":
                    party = party + values;
                    break;
                case "Friends":
                    friends = friends + values;
                    break;
            }

        }
        if (animal > 0) barEntries.add(new BarEntry(0, animal));
        if (home > 0) barEntries.add(new BarEntry(1, home));
        if (games > 0) barEntries.add(new BarEntry(2, games));
        if (food > 0) barEntries.add(new BarEntry(3, food));
        if (waste > 0) barEntries.add(new BarEntry(4, waste));
        if (party > 0) barEntries.add(new BarEntry(5, party));
        if (friends > 0) barEntries.add(new BarEntry(6, friends));
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.calcMinMax();
        //add colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorOrange));
        colors.add(getResources().getColor(R.color.colorPrimary));
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        dataSet.setColors(colors);
//        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(15f);
        BarData barData = new BarData(dataSet);
        barChart = view.findViewById(R.id.bar_chart);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.animateY(500);
    }

    private void setupPieChart(View view, List<Expense> expenses) {
        //Add items as entries
        List<PieEntry> pieEntries = new ArrayList<>();
        float animal = 0;
        float home = 0;
        float games = 0;
        float food = 0;
        float waste = 0;
        float party = 0;
        float friends = 0;

        for (int i = 0; i < expenses.size(); i++) {
            float values = Float.parseFloat(expenses.get(i).getSum());
            switch (expenses.get(i).getIconDesc()) {
                case "Animal":
                    animal = animal + values;
                    break;
                case "Home":
                    home = home + values;
                    break;
                case "Games":
                    games = games + values;
                    break;
                case "Food":
                    food = food + values;
                    break;
                case "Waste":
                    waste = waste + values;
                    break;
                case "Party":
                    party = party + values;
                    break;
                case "Friends":
                    friends = friends + values;
                    break;
            }
        }
        if (animal > 0) pieEntries.add(new PieEntry(animal, "Animal"));
        if (home > 0) pieEntries.add(new PieEntry(home, "Home"));
        if (games > 0) pieEntries.add(new PieEntry(games, "Games"));
        if (food > 0) pieEntries.add(new PieEntry(food, "Food"));
        if (waste > 0) pieEntries.add(new PieEntry(waste, "Waste"));
        if (party > 0) pieEntries.add(new PieEntry(party, "Party"));
        if (friends > 0) pieEntries.add(new PieEntry(friends, "Friends"));


        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setSliceSpace(1f);
        //add colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorOrange));
        colors.add(getResources().getColor(R.color.colorPrimary));
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        dataSet.setColors(colors);
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