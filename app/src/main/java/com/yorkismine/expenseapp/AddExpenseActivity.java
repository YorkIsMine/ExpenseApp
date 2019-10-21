package com.yorkismine.expenseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddExpenseActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_DESC = "DESC";
    public static final String EXTRA_SUM = "SUM";


    private FloatingActionButton fab;
    private EditText edtTitle;
    private EditText edtDesc;
    private EditText edtSum;
    private Button btnPlus;
    private Button btnMinus;
    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setTitle("Add Expense");

        edtTitle = findViewById(R.id.add_edt_title);
        edtDesc = findViewById(R.id.add_edt_desc);
        edtSum = findViewById(R.id.add_edt_sum);
        btnPlus = findViewById(R.id.sum_btn_plus);
        btnMinus = findViewById(R.id.sum_btn_minus);

//        addSum(edtSum.getText().toString());
        Log.d("EditSum", "Sum: " + sum);
        sum = Integer.parseInt(edtSum.getText().toString());
        addSum();

        fab = findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
            }
        });
    }

    private void saveExpense(){
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String sum = edtSum.getText().toString();

        if(title.trim().isEmpty() || desc.trim().isEmpty() || sum.equals("0")){
            Toast.makeText(this, "Insert all fields!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, desc);
        data.putExtra(EXTRA_SUM, sum);

        setResult(RESULT_OK, data);
        finish();
    }

    //TODO fix calculate sum method -> add observer or livedata
    private void addSum() {
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum == 0){
                    sum++;
                }else {
                    sum = sum + 1;
                    String s = String.valueOf(sum);
                    edtSum.setText(s);
                    Log.d("EditSum", "Sum: " + sum);
                }

            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum > 0){
                    sum = sum - 1;
                    String s = String.valueOf(sum);
                    edtSum.setText(s);
                    Log.d("EditSum", "Sum: " + sum);
                }
            }
        });
    }
}
