package com.example.philonoist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NotificationListAdapter extends ArrayAdapter<Notifications> {
    private Context context;
    private List<Notifications> notifications;

    public NotificationListAdapter(Context context, List<Notifications> notifications){
        super(context, R.layout.row_notifications_layout, notifications);

        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.row_notifications_layout, parent, false);

        ImageView ivAccept = convertView.findViewById(R.id.ivRowNotificationLayout_accept);
        ImageView ivRequest = convertView.findViewById(R.id.ivRowNotificationLayout_request);
        TextView tvMessage = convertView.findViewById(R.id.tvRowNotificationLayout_message);

        ivAccept.setVisibility(View.GONE);
        ivRequest.setVisibility(View.GONE);
        String message;
        Log.i("ifnCheck", "in if: " + notifications.get(position).getTeacher_email());

        if(notifications.get(position).getTeacher_email().equals("Null")){
            ivAccept.setVisibility(View.VISIBLE);
            message = notifications.get(position).getMessage();
            message += " " + notifications.get(position).getStudent_name();
        }else{
            ivRequest.setVisibility(View.VISIBLE);
            message = notifications.get(position).getTeacher_email();
            message += " " + notifications.get(position).getMessage();
        }
        tvMessage.setText(message);

        return convertView;
    }
}
