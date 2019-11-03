package com.yorkismine.expenseapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yorkismine.expenseapp.dialog.TypeDialog;
import com.yorkismine.expenseapp.model.Expense;
import com.yorkismine.expenseapp.model.ExpenseViewModel;
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

public class AddExpenseBottomDialog extends BottomSheetDialogFragment {
    private ExpenseViewModel expenseViewModel;

    private Button exFab;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_expense_bottom_dialog, container, false);
        expenseViewModel =
                ViewModelProviders.of(this).get(ExpenseViewModel.class);

        edtTitle = view.findViewById(R.id.add_edt_title);
        edtDesc = view.findViewById(R.id.add_edt_desc);
        edtSum = view.findViewById(R.id.add_edt_sum);
        cvDate = view.findViewById(R.id.add_cv_date);
        tvDate = view.findViewById(R.id.add_tv_date);
        tvCurrency = view.findViewById(R.id.add_tv_currency);
        tvCurrency.setText(EXTRA_CURRENCY);

        typeExpense = view.findViewById(R.id.add_type);
        tvTypeExpense = view.findViewById(R.id.add_tv_type);
        typeImgExpense = view.findViewById(R.id.add_icon_type);
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
                dialog.show(getActivity().getSupportFragmentManager(), "TAG");
            }
        });

        //ToDo create custom dialog datePicker
        cvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

        exFab = view.findViewById(R.id.fab_add_expense);
        exFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
            }
        });

        return view;
    }
    private void saveExpense() {
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String sum = edtSum.getText().toString();
        String typeDesc = tvTypeExpense.getText().toString();
        int icon = 0;
        for(TypeOfExpense type : types){
            if(typeImgExpense.getId() == type.getImageView()){
                icon = type.getImageView();
            }
        }

        if (sum.trim().length() > 7) {
            Toast.makeText(getActivity(), "Max sum 7 digits!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if (title.trim().isEmpty() || desc.trim().isEmpty() || sum.isEmpty() || dateInMillis.isEmpty() || typeDesc.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Insert all fields!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        

        Expense expense = new Expense(title, desc, sum, EXTRA_CURRENCY, dateInMillis, icon, typeDesc);
        expenseViewModel.insert(expense);

        this.dismiss();
    }

}
