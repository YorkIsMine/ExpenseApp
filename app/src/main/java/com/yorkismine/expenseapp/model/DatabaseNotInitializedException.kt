package com.yorkismine.expenseapp.model

class DatabaseNotInitializedException(
    message: String = "Database not initialized yet"
) : DatabaseException(message)