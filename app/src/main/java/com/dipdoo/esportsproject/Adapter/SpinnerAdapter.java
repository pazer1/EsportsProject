package com.dipdoo.esportsproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dipdoo.esportsproject.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    String[] category;
    int[] categoryImage;

    public SpinnerAdapter(Context context, String[] category, int[] categoryImage) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.category = category;
        this.categoryImage = categoryImage;
    }

    @Override
    public int getCount() {
        if(category != null) return category.length;
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return category[position];
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.spinner_normal,parent,false);
        }
        if(category !=null){
            String text = category[position];
            int img= categoryImage[position];
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
            ((ImageView)convertView.findViewById(R.id.spinner_img)).setBackgroundResource(categoryImage[position]);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.spinner_dropdown,parent,false);

        }
        if(category !=null){
            String text = category[position];
            int img= categoryImage[position];
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
            ((ImageView)convertView.findViewById(R.id.spinner_img)).setBackgroundResource(categoryImage[position]);

        }
        return convertView;
    }
}
