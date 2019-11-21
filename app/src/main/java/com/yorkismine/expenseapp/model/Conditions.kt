package com.yorkismine.expenseapp.model

interface Conditions<E> {
    fun check(e: E): Boolean
}