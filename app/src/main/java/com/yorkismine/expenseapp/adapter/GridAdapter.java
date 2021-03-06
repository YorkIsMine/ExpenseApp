package com.yorkismine.expenseapp.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.dialog.DismissListener;
import com.yorkismine.expenseapp.model.TypeOfExpense;
import com.yorkismine.expenseapp.singleton.ExpenseUtil;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private DismissListener dismissListener;
    private ArrayList<Integer> images;
    private TextView typeTextView;
    private ArrayList<TypeOfExpense> types = ExpenseUtil.getTypes();
    private ImageView typeImageView;

    public GridAdapter(Context context, DismissListener dismissListener, ArrayList<Integer> images, TextView typeTextView, ImageView typeImageView) {
        this.context = context;
        this.dismissListener = dismissListener;
        this.images = images;
        this.typeTextView = typeTextView;
        this.typeImageView = typeImageView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public TypeOfExpense type;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        type = types.get(i);
        final TypeHolder holder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.dialog_type_item, viewGroup, false);
            holder = new TypeHolder(view);
            view.setTag(holder);
        }else holder = (TypeHolder) view.getTag();

        holder.textView.setText(type.getType());
        holder.imageView.setImageResource(type.getImageView());



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeImageView.setImageDrawable(holder.imageView.getDrawable());
                typeTextView.setText(holder.textView.getText());
                Toast.makeText(view.getContext(), pos + "",  Toast.LENGTH_LONG).show();
                dismissListener.listen();
            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissListener.listen();
            }
        });
        return view;
    }

    public static class TypeHolder{
        private ImageView imageView;
        private TextView textView;

        public TypeHolder(View view){
            imageView = view.findViewById(R.id.image_view_type);
            textView = view.findViewById(R.id.text_view_type);
        }
    }
}
