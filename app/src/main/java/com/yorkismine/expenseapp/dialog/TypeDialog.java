package com.yorkismine.expenseapp.dialog;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.adapter.GridAdapter;
import com.yorkismine.expenseapp.model.TypeOfExpense;

import java.util.ArrayList;

public class TypeDialog extends DialogFragment implements DismissListener {
    private ArrayList<TypeOfExpense> types = new ArrayList<>();
    private TextView typeTextView;
    private ImageView typeImageView;

    public TypeDialog(TextView typeTextView, ImageView typeImageView) {
        this.typeTextView = typeTextView;
        this.typeImageView = typeImageView;
    }

    //ToDo need refactoring
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_type, container, false);
        GridView gridView = view.findViewById(R.id.dialog_grid);
        initList();
        ArrayList<Integer> images = new ArrayList<>();
        for(TypeOfExpense type : types){
            images.add(type.getImageView());
        }

        GridAdapter adapter = new GridAdapter(getActivity(), this, images, typeTextView, types, typeImageView);
        gridView.setAdapter(adapter);

        return view;
    }

    @Override
    public void listen() {
        this.dismiss();
    }

    private void initList(){
        for (int i = 0; i < 10; i++){
            types.add(new TypeOfExpense("Animal " + i, R.drawable.ic_launcher_foreground));
        }
    }
}

