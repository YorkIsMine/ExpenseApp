package com.yorkismine.expenseapp.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Expense.class}, version = 2, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase {
    private static ExpenseDatabase instance;

    public abstract ExpenseDAO expenseDAO();

    public static void init(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ExpenseDatabase.class,
                    "expenses_v1"
            ).addMigrations(new MigrationToNewV(1, 2))
                    .build();
        } else {
            throw new DatabaseAlreadyInitializedException();
        }
    }

    public static synchronized ExpenseDatabase getInstance() {
        if(instance != null)
            return instance;
        else
            throw new DatabaseAlreadyInitializedException();
    }
    static class MigrationToNewV extends Migration{

        /**
         * Creates a new migration between {@code startVersion} and {@code endVersion}.
         *
         * @param startVersion The start version of the database.
         * @param endVersion   The end version of the database after this migration is applied.
         */
        public MigrationToNewV(int startVersion, int endVersion) {
            super(startVersion, endVersion);
        }

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.beginTransaction();

        }
    }
}
