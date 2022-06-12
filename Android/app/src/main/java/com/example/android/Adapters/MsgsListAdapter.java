package com.example.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Data.Message;
import com.example.android.R;

import java.util.List;


public class MsgsListAdapter extends RecyclerView.Adapter<MsgsListAdapter.MsgsViewHolder> {
    class MsgsViewHolder extends RecyclerView.ViewHolder{
        private final TextView content;
        private final TextView Datetime;

        private MsgsViewHolder(View itemView) {
            super(itemView);
            this.content = itemView.findViewById(R.id.textMessage);
            Datetime = itemView.findViewById(R.id.textDateTime);
        }
    }

    private final LayoutInflater mInflater;
    private List<Message> messagelist;

    //constructor
    public MsgsListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MsgsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_container_recieved_message, parent, false); //todo -supprot different msg types
        return new MsgsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgsViewHolder holder, int position) {
        if(messagelist != null){
            final Message current = messagelist.get(position);
            holder.content.setText(current.getContent());
            holder.Datetime.setText(current.getCreated());
        }
    }

    public void setMsgs(List<Message> msgs) {
        this.messagelist = msgs;
    }

    @Override
    public int getItemCount() {
        if (messagelist!=null)
            return messagelist.size();
        else return 0;
    }

    public List<Message> getMsgslist() {
        return messagelist;
    }
}
