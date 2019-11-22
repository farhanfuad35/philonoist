package com.example.philonoist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TuitionListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Offer> tuitionList;
    ArrayList<Offer> arrayList;

    public TuitionListAdapter(Context context, List<Offer> tuitionList) {
        this.context = context;
        this.tuitionList = tuitionList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Offer>();
        this.arrayList.addAll(tuitionList);
    }

    public class ViewHolder{
        TextView title;
        TextView salary;
    }

    @Override
    public int getCount() {
        return tuitionList.size();
    }

    @Override
    public Object getItem(int i) {
        return tuitionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row, null);

            holder.title = view.findViewById(R.id.tvTuitionList_title);
            holder.salary = view.findViewById(R.id.tvTuitionList_salary);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)view.getTag();
        }

        holder.title.setText(tuitionList.get(position).getName());
        holder.salary.setText(tuitionList.get(position).getSalary());
        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        tuitionList.clear();
        if(charText.length() == 0){
            tuitionList.addAll(arrayList);
        }

        else{
            for(Offer tuition : arrayList){
                if(tuition.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    tuitionList.add(tuition);
                }
                if(tuition.getSalary().toLowerCase(Locale.getDefault()).contains(charText)){
                    tuitionList.add(tuition);
                }
            }
        }
        notifyDataSetChanged();
    }

}
