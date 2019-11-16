package com.yorkismine.expenseapp.model

class DatabaseAlreadyInitializedException(
    message: String = "Database already initialized!"
) : DatabaseException(message)