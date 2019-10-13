package com.example.philonoist;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TuitionListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Tuition> tuitionList;
    ArrayList<Tuition> arrayList;

    public TuitionListAdapter(Context context, List<Tuition> tuitionList) {
        this.context = context;
        this.tuitionList = tuitionList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Tuition>();
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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tuitionList.get(position).getName().equals("Farhan Fuad")){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.TuitionDetails.class);
                    view.getContext().startActivity(intent);
                }
                //Toast.makeText(context, "Item Clicked in Adapter", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        tuitionList.clear();
        if(charText.length() == 0){
            tuitionList.addAll(arrayList);
        }

        else{
            for(Tuition tuition : arrayList){
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
