package com.yorkismine.expenseapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yorkismine.expenseapp.dialog.TypeDialog;
import com.yorkismine.expenseapp.model.TypeOfExpense;
import com.yorkismine.expenseapp.singleton.ExpenseUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DATE;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_DESC;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_ICON;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_ICON_DESC;
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
    private ImageView typeImgExpense;

    private String date;
    private String dateInMillis;
    private ArrayList<TypeOfExpense> types = ExpenseUtil.getTypes();

//    private String[] strings = new String[]{
//      "adada",
//      "dadadadada",
//      "dadadada",
//      "HELLLO!!!!!!!",
//      "adada",
//      "sfseasdsf",
//      "lskfkslfksf",
//      "dadada",
//      "kuku",
//      "Item"
//    };

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
        typeImgExpense = findViewById(R.id.add_icon_type);
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




//        typeExpense.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GridView gridView = new GridView(AddExpenseActivity.this);
//                final AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenseActivity.this);
//                builder.setView(gridView);
//                builder.setTitle("Choose category of expense:");
//                gridView.setNumColumns(2);
//                final AlertDialog alertDialog = builder.create();
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddExpenseActivity.this, android.R.layout.simple_list_item_1, strings);
//                gridView.setAdapter(adapter);
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(AddExpenseActivity.this,
//                                "You tapped" + position, Toast.LENGTH_LONG).show();
//                        typeExpense.setText(position + "");
//                        alertDialog.dismiss();
//                    }
//                });
//
//
//                alertDialog.show();
//            }
//        });

        typeExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TypeDialog dialog = new TypeDialog(typeExpense, typeImgExpense);
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

    //ToDo need some refactoring (ForEach)
    private void saveExpense() {
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String sum = edtSum.getText().toString();
        String typeDesc = typeExpense.getText().toString();
        int icon = 0;
        for(TypeOfExpense type : types){
            if(typeImgExpense.getId() == type.getImageView()){
                icon = type.getImageView();
            }

        }


        if (sum.trim().length() > 7) {
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
        data.putExtra(EXTRA_ICON, icon);
        data.putExtra(EXTRA_ICON_DESC, typeDesc);


        setResult(RESULT_OK, data);
        finish();
    }

}
