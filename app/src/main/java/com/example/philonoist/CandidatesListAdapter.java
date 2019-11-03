package com.example.philonoist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class CandidatesListAdapter extends ArrayAdapter<BackendlessUser> {
    private Context context;
    private List<BackendlessUser> candidates;

    public CandidatesListAdapter(Context context, List<BackendlessUser> candidates){

        super(context, R.layout.row_candidate_list_layout, candidates);
        this.context = context;
        this.candidates = candidates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.row_candidate_list_layout, parent, false);

        TextView tvChar = convertView.findViewById(R.id.tvChar);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvMail = convertView.findViewById(R.id.tvMail);

        String firstName = (String) candidates.get(position).getProperty("first_name");
        tvChar.setText(firstName.toUpperCase().charAt(0) + "");
        String lastName = (String) candidates.get(position).getProperty("last_name");
        tvName.setText(firstName + " " + lastName);
        tvMail.setText(candidates.get(position).getEmail());

        return  convertView;
    }
}
