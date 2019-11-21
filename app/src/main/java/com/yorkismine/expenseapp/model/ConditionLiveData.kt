package com.yorkismine.expenseapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlin.properties.Delegates

class ConditionLiveData(
    firstLiveData: LiveData<Int>,
    secondLiveData: LiveData<Int>
) : MediatorLiveData<Int>() {

    private var first = 0
    private var second = 0

    init {
        addSource(firstLiveData) {
            first = it
            value = first or second
        }
        addSource(secondLiveData) {
            second = it
            value = first or second
        }
    }


}