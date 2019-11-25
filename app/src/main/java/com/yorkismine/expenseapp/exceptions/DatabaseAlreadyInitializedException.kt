package com.yorkismine.expenseapp.exceptions

class DatabaseAlreadyInitializedException(
    message: String = "Database already initialized!"
) : DatabaseException(message)