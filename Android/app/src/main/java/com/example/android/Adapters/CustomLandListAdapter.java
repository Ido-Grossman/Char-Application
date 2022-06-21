package com.example.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.Data.Contact;
import com.example.android.R;

import java.util.ArrayList;

public class CustomLandListAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;

    public CustomLandListAdapter(Context ctx, ArrayList<Contact> contactArrayList) {
        super(ctx, R.layout.contact_list_item_landscape, contactArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_list_item_landscape, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView lastMsg = convertView.findViewById(R.id.last_massage);
        TextView time = convertView.findViewById(R.id.time);

        imageView.setImageResource(R.mipmap.ic_launcher_foreground); //todo
        userName.setText(contact.getName());
        lastMsg.setText(contact.getLast());
        time.setText(contact.getLastDate());

        return convertView;
    }
}
