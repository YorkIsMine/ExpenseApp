package com.yorkismine.expenseapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DATE;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DESC;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_SUM;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_TITLE;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_TYPE;

public class AddExpenseActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private EditText edtTitle;
    private EditText edtDesc;
    private EditText edtSum;
    private TextView cvDate;
    private TextView typeExpense;

    private String date;
    private String dateInMillis;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setTitle("Add Expense");

        edtTitle = findViewById(R.id.add_edt_title);
        edtDesc = findViewById(R.id.add_edt_desc);
        edtSum = findViewById(R.id.add_edt_sum);
        cvDate = findViewById(R.id.add_date);
        typeExpense = findViewById(R.id.add_type);

        //Getting anf Setting date from CalendarView
//        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//
//                GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
////                Date hireDay = calendar.getTime();
////                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
////                date = sdf.format(hireDay);
//                dateInMillis = String.valueOf(calendar.getTimeInMillis());
//
////                Log.d("TAG", "Date clicked: " + sdf.format(hireDay));
//                Log.d("TAG", "Date clicked in milliseconds: " + dateInMillis);
//            }
//        });

        typeExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //ToDo create custom dialog datePicker
        cvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(AddExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar mCalendar = new GregorianCalendar(i, i1, i2);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                        date = sdf.format(mCalendar.getTime());
                        dateInMillis = String.valueOf(mCalendar.getTimeInMillis());
                        cvDate.setText(date);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                dpd.show();
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

        if (sum.trim().length() > 10) {
            Toast.makeText(this, "Max sum 10 digits!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
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
