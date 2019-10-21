package com.yorkismine.expenseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddExpenseActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.project.AddExpenseActivity.title";
    public static final String EXTRA_DESC = "com.project.AddExpenseActivity.desc";

    private FloatingActionButton fab;
    private EditText editTextTitle;
    private EditText editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setTitle("Add Expense");

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDesc = findViewById(R.id.edit_text_desc);

        fab = findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
            }
        });
    }

    private void saveExpense(){
        String title = editTextTitle.getText().toString();
        String desc = editTextDesc.getText().toString();

        if(title.trim().isEmpty() || desc.trim().isEmpty()){
            Toast.makeText(this, "Insert all fields!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, desc);

        setResult(RESULT_OK, data);
        finish();
    }
}
