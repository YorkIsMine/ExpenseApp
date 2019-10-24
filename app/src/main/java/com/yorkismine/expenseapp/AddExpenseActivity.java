package com.yorkismine.expenseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

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
    private String date;
    private String dateInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setTitle("Add Expense");

        edtTitle = findViewById(R.id.add_edt_title);
        edtDesc = findViewById(R.id.add_edt_desc);
        edtSum = findViewById(R.id.add_edt_sum);
        cvDate = findViewById(R.id.add_cv_date);

        //Getting anf Setting date from CalendarView
        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
//                Date hireDay = calendar.getTime();
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
//                date = sdf.format(hireDay);
                dateInMillis = String.valueOf(calendar.getTimeInMillis());

//                Log.d("TAG", "Date clicked: " + sdf.format(hireDay));
                Log.d("TAG", "Date clicked in milliseconds: " + dateInMillis);
            }
        });

        fab = findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
            }
        });
    }

    private void saveExpense() {
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String sum = edtSum.getText().toString();

        if (dateInMillis.trim().isEmpty() || dateInMillis.trim().equals(" ")) {
            dateInMillis = "01.01.19";
        }
        if (title.trim().isEmpty() || desc.trim().isEmpty() || sum.isEmpty() || dateInMillis.isEmpty()) {
            Toast.makeText(this, "Insert all fields!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, desc);
        data.putExtra(EXTRA_SUM, sum);
        data.putExtra(EXTRA_DATE, dateInMillis);

        setResult(RESULT_OK, data);
        finish();
    }

}
