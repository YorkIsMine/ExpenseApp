package com.yorkismine.expenseapp.utils

import android.annotation.SuppressLint
import com.yorkismine.expenseapp.model.Conditions
import com.yorkismine.expenseapp.model.Expense
import com.yorkismine.expenseapp.utils.Constants.*
import java.text.SimpleDateFormat
import java.util.*

object FilterUtils {

    private val conditionByDay by lazy {
        object : Conditions<Expense> {
            @SuppressLint("SimpleDateFormat")
            override fun check(e: Expense): Boolean {
                if (e.date == null) return false
                val dayS = SimpleDateFormat("dd MM yyyy")
                val dateFrom = Date(e.date!!)
                return dayS.format(dateFrom) == dayS.format(Date())
            }
        }
    }

    private val conditionByWeek by lazy {
        object : Conditions<Expense> {
            @SuppressLint("SimpleDateFormat")
            override fun check(e: Expense): Boolean {
                if (e.date == null) return false
                val dayS = SimpleDateFormat("w yyyy")
                val dateFrom = Date(e.date!!)
                return dayS.format(dateFrom) == dayS.format(Date())
            }
        }
    }

    private val conditionByMonth by lazy {
        object : Conditions<Expense> {
            @SuppressLint("SimpleDateFormat")
            override fun check(e: Expense): Boolean {
                if (e.date == null) return false
                val dayS = SimpleDateFormat("MM yyyy")
                val dateFrom = Date(e.date!!)
                return dayS.format(dateFrom) == dayS.format(Date())
            }
        }
    }

    private val conditionByYear by lazy {
        object : Conditions<Expense> {
            @SuppressLint("SimpleDateFormat")
            override fun check(e: Expense): Boolean {
                if (e.date == null) return false
                val dayS = SimpleDateFormat("yyyy")
                val dateFrom = Date(e.date!!)
                return dayS.format(dateFrom) == dayS.format(Date())
            }
        }
    }

    fun getConditionByType(type: Int): Conditions<Expense> {
        return when (type) {
            SHOW_BY_DAY -> {
                conditionByDay
            }
            SHOW_BY_WEEK -> {
                conditionByWeek
            }
            SHOW_BY_MONTH -> {
                conditionByMonth
            }
            SHOW_BY_YEAR -> {
                conditionByYear
            }
            else -> {
                conditionByMonth
            }
        }
    }

    fun getComparatorByType(type: Int): Comparator<Expense>? {
        return when (type) {
            SORT_BY_DATE -> Comparator<Expense> { p0, p1 -> (p0!!.date)!!.compareTo(p1!!.date!!) }
            SORT_BY_NAME -> Comparator<Expense>{ p0, p1 -> (p0!!.title)!!.compareTo(p1!!.title!!)}
            SORT_BY_SUM -> Comparator<Expense>{ p0, p1 -> (p0!!.sum)!!.compareTo(p1!!.sum!!)}
            else -> null //ToDo вернуть дефолтный компаратор
        }
    }
}