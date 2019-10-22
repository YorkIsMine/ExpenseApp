package com.yorkismine.expenseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DATE;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DESC;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_SUM;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_TITLE;

public class AddExpenseActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private EditText edtTitle;
    private EditText edtDesc;
    private EditText edtSum;
    private CalendarView cvDate;
    private Button btnPlus;
    private Button btnMinus;
//    private int sum;
    private SimpleDateFormat dateFormat;
    private Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setTitle("Add Expense");

        edtTitle = findViewById(R.id.add_edt_title);
        edtDesc = findViewById(R.id.add_edt_desc);
        edtSum = findViewById(R.id.add_edt_sum);
        cvDate = findViewById(R.id.add_cv_date);
        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //TODO implements date picker
            }
        });
        btnPlus = findViewById(R.id.sum_btn_plus);
        btnMinus = findViewById(R.id.sum_btn_minus);

        fab = findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void saveExpense() {
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String sum = edtSum.getText().toString();

        //Getting anf Setting date from CalendarView
        long date = cvDate.getDate();
        currentDate = new Date(date);
        dateFormat = new SimpleDateFormat("dd.MM.yy");
        String dateSting = dateFormat.format(currentDate);

        if (title.trim().isEmpty() || desc.trim().isEmpty() || sum.isEmpty()) {
            Toast.makeText(this, "Insert all fields!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, desc);
        data.putExtra(EXTRA_SUM, sum);
        data.putExtra(EXTRA_DATE, dateSting);

        setResult(RESULT_OK, data);
        finish();
    }

    //TODO fix calculate sum method -> add observer or livedata
//    private void addSum() {
//        btnPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (sum == 0) {
//                    sum++;
//                } else {
//                    sum = sum + 1;
//                    String s = String.valueOf(sum);
//                    edtSum.setText(s);
//                    Log.d("EditSum", "Sum: " + sum);
//                }
//
//            }
//        });
//        btnMinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (sum > 0) {
//                    sum = sum - 1;
//                    String s = String.valueOf(sum);
//                    edtSum.setText(s);
//                    Log.d("EditSum", "Sum: " + sum);
//                }
//            }
//        });
//    }
}
