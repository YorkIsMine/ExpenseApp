package com.yorkismine.expenseapp.model

import android.annotation.SuppressLint
import com.yorkismine.expenseapp.utils.Constants.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator as Comparator1

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
        val dateComparator = object : Comparator<Expense>{
            override fun compare(p0: Expense?, p1: Expense?): Int {
                return (p0!!.date)!!.compareTo(p1!!.date!!)
            }
        }

        val nameComparator = object : Comparator<Expense>{
            override fun compare(p0: Expense?, p1: Expense?): Int {
                return (p0!!.date)!!.compareTo(p1!!.date!!)
            }
        }

        val sumComparator = object : Comparator<Expense>{
            override fun compare(p0: Expense?, p1: Expense?): Int {
                return (p0!!.date)!!.compareTo(p1!!.date!!)
            }
        }

        return when (type) {
            SORT_BY_DATE -> dateComparator
            SORT_BY_NAME -> nameComparator
            SORT_BY_SUM -> sumComparator
            else -> null //ToDo вернуть дефолтный компаратор
        }
    }
}