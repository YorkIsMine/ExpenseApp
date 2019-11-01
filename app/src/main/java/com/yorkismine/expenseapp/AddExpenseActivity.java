package com.yorkismine.expenseapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yorkismine.expenseapp.dialog.TypeDialog;
import com.yorkismine.expenseapp.model.TypeOfExpense;
import com.yorkismine.expenseapp.singleton.ExpenseUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DATE;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DESC;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_ICON;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_ICON_DESC;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_SUM;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_TITLE;

public class AddExpenseActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private EditText edtTitle;
    private EditText edtDesc;
    private EditText edtSum;
    private MaterialCardView cvDate;
    private TextView tvTypeExpense;
    private MaterialCardView typeExpense;
    private ImageView typeImgExpense;

    private String date;
    private String dateInMillis;
    private TextView tvCurrency;
    private TextView tvDate;
    private ArrayList<TypeOfExpense> types = ExpenseUtil.getTypes();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setTitle("Add Expense");

        edtTitle = findViewById(R.id.add_edt_title);
        edtDesc = findViewById(R.id.add_edt_desc);
        edtSum = findViewById(R.id.add_edt_sum);
        cvDate = findViewById(R.id.add_cv_date);
        tvDate = findViewById(R.id.add_tv_date);
        tvCurrency = findViewById(R.id.add_tv_currency);
        tvCurrency.setText(EXTRA_CURRENCY);

        typeExpense = findViewById(R.id.add_type);
        tvTypeExpense = findViewById(R.id.add_tv_type);
        typeImgExpense = findViewById(R.id.add_icon_type);
        typeImgExpense.setImageResource(R.drawable.ic_type);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Calendar mCalendar = Calendar.getInstance();
        date = sdf.format(mCalendar.getTime());
        dateInMillis = String.valueOf(mCalendar.getTimeInMillis());
        tvDate.setText(date);

        typeExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TypeDialog dialog = new TypeDialog(tvTypeExpense, typeImgExpense);
                typeImgExpense.setBackground(null);
                dialog.show(getSupportFragmentManager(), "TAG");
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
                        tvDate.setText(date);
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

    //ToDo need some refactoring (ForEach)
    private void saveExpense() {
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String sum = edtSum.getText().toString();
        String typeDesc = tvTypeExpense.getText().toString();
        int icon = 0;
        for (TypeOfExpense type : types) {
            if (typeImgExpense.getId() == type.getImageView()) {
                icon = type.getImageView();
            }
        }

        if (sum.trim().length() > 7) {
            Toast.makeText(this, "Max sum 7 digits!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if (title.trim().isEmpty() || desc.trim().isEmpty() || sum.isEmpty() || dateInMillis.isEmpty() || typeDesc.trim().isEmpty()) {
            Toast.makeText(this, "Insert all fields!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, desc);
        data.putExtra(EXTRA_SUM, sum);
        data.putExtra(EXTRA_DATE, dateInMillis);
        data.putExtra(EXTRA_ICON, icon);
        data.putExtra(EXTRA_ICON_DESC, typeDesc);


        setResult(RESULT_OK, data);
        finish();
    }

}
