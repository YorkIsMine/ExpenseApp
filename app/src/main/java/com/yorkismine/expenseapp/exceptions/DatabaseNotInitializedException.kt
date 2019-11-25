package com.yorkismine.expenseapp.exceptions

import com.yorkismine.expenseapp.exceptions.DatabaseException

class DatabaseNotInitializedException(
    message: String = "Database not initialized yet"
) : DatabaseException(message)