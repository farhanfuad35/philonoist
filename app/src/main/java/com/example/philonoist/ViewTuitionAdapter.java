package com.example.philonoist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ViewTuitionAdapter extends ArrayAdapter<Offer> {

    private Context context;
    private List<Offer> tuitions;

    public ViewTuitionAdapter(Context context, List<Offer> offers) {

        super(context, R.layout.row_tuition_list_layout, offers);
        this.context = context;
        this.tuitions = offers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.row_tuition_list_layout, parent, false);

        TextView tvClass = convertView.findViewById(R.id.tvClass);
        TextView tvClassStatus = convertView.findViewById(R.id.tvClassStatus);
        TextView tvSubject = convertView.findViewById(R.id.tvSubject);
        TextView tvSalary = convertView.findViewById(R.id.tvSalary);

        tvClass.setText("Class");
        tvClassStatus.setText(tuitions.get(position).get_class());
        tvSubject.setText("Subject: " + tuitions.get(position).getSubject());
        tvSalary.setText("Remuneration: " + tuitions.get(position).getSalary());

        return convertView;
    }
}