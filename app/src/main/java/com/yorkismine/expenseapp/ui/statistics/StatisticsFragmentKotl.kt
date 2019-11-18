package com.yorkismine.expenseapp.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.yorkismine.expenseapp.R
import com.yorkismine.expenseapp.model.Expense
import com.yorkismine.expenseapp.model.ExpenseKotl
import java.util.ArrayList

class StatisticsFragmentKotl : Fragment() {

    private var dashboardViewModel: StatisticsViewModelKotl? = null
    private var pieChart: PieChart? = null
    private var barChart: BarChart? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(StatisticsViewModelKotl::class.java)
        val root = inflater.inflate(R.layout.fragment_statistics, container, false)
        dashboardViewModel!!.allExpenses.observe(this, Observer { expenses ->
            setupPieChart(root, expenses)
            setupBarChart(root, expenses)
        })
        return root
    }

    private fun setupBarChart(view: View, expenses: List<ExpenseKotl>) {
        val barEntries = ArrayList<BarEntry>()
        //        for (int i = 0; i < expenses.size(); i++) {
        //            float y = Float.parseFloat(expenses.get(i).getSum());
        //            String x = expenses.get(i).getTitle();
        //            barEntries.add(new BarEntry(i, y));
        //        }
        val sum = 0f
        var animal = 0f
        var home = 0f
        var games = 0f
        var food = 0f
        var waste = 0f
        var party = 0f
        var friends = 0f

        for (i in expenses.indices) {
            val values = expenses[i].sum
            when (expenses[i].iconDesc) {
                "Animal" -> animal += values!!
                "Home" -> home += values!!
                "Games" -> games += values!!
                "Food" -> food += values!!
                "Waste" -> waste += values!!
                "Party" -> party += values!!
                "Friends" -> friends += values!!
            }

        }
        if (animal > 0) barEntries.add(BarEntry(0f, animal))
        if (home > 0) barEntries.add(BarEntry(1f, home))
        if (games > 0) barEntries.add(BarEntry(2f, games))
        if (food > 0) barEntries.add(BarEntry(3f, food))
        if (waste > 0) barEntries.add(BarEntry(4f, waste))
        if (party > 0) barEntries.add(BarEntry(5f, party))
        if (friends > 0) barEntries.add(BarEntry(6f, friends))
        val dataSet = BarDataSet(barEntries, "")
        dataSet.calcMinMax()
        //add colors
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.colorOrange))
        colors.add(resources.getColor(R.color.colorPrimary))
        colors.add(Color.RED)
        colors.add(Color.GREEN)
        colors.add(Color.CYAN)
        colors.add(Color.MAGENTA)
        dataSet.colors = colors
        //        dataSet.setDrawValues(true);
        dataSet.valueTextSize = 15f
        val barData = BarData(dataSet)
        barChart = view.findViewById(R.id.bar_chart)
        barChart!!.setFitBars(true)
        barChart!!.data = barData
        barChart!!.invalidate()
        barChart!!.animateY(500)
    }

    private fun setupPieChart(view: View, expenses: List<ExpenseKotl>) {
        //Add items as entries
        val pieEntries = ArrayList<PieEntry>()
        var animal = 0f
        var home = 0f
        var games = 0f
        var food = 0f
        var waste = 0f
        var party = 0f
        var friends = 0f

        for (i in expenses.indices) {
            val values = expenses[i].sum
            when (expenses[i].iconDesc) {
                "Animal" -> animal += values!!
                "Home" -> home += values!!
                "Games" -> games += values!!
                "Food" -> food += values!!
                "Waste" -> waste += values!!
                "Party" -> party += values!!
                "Friends" -> friends += values!!
            }
        }
        if (animal > 0) pieEntries.add(PieEntry(animal, "Animal"))
        if (home > 0) pieEntries.add(PieEntry(home, "Home"))
        if (games > 0) pieEntries.add(PieEntry(games, "Games"))
        if (food > 0) pieEntries.add(PieEntry(food, "Food"))
        if (waste > 0) pieEntries.add(PieEntry(waste, "Waste"))
        if (party > 0) pieEntries.add(PieEntry(party, "Party"))
        if (friends > 0) pieEntries.add(PieEntry(friends, "Friends"))


        val dataSet = PieDataSet(pieEntries, "")
        dataSet.sliceSpace = 1f
        //add colors
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.colorOrange))
        colors.add(resources.getColor(R.color.colorPrimary))
        colors.add(Color.RED)
        colors.add(Color.GREEN)
        colors.add(Color.CYAN)
        colors.add(Color.MAGENTA)
        dataSet.colors = colors
        dataSet.setDrawValues(false)
        dataSet.selectionShift = 10f

        val pieData = PieData(dataSet)
        // Get the chart
        pieChart = view.findViewById(R.id.pie_chart)
        pieChart!!.holeRadius = 20f
        pieChart!!.minAngleForSlices = 30f
        pieChart!!.setTransparentCircleAlpha(0)
        pieChart!!.data = pieData
        pieChart!!.invalidate()
        pieChart!!.animateY(500)
    }
}