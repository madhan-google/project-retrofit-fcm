package com.codekiller.retrofitwithfcm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRecycler extends RecyclerView.Adapter<MainRecycler.ViewHolder> {
    Context context;
    ArrayList<JSONFile> arrayList;

    public MainRecycler(Context context, ArrayList<JSONFile> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_recycler,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONFile jsonFile = arrayList.get(position);
        holder.titleVIew.setText("TITLE : "+jsonFile.getTitle());
        holder.idView.setText("ID : "+jsonFile.getId());
        holder.userIdView.setText("USER ID :"+jsonFile.getUserId());
        holder.bodyView.setText("BODY : "+jsonFile.getBody());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleVIew, bodyView, idView, userIdView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleVIew = itemView.findViewById(R.id.title);
            bodyView = itemView.findViewById(R.id.body);
            idView = itemView.findViewById(R.id.text_id);
            userIdView = itemView.findViewById(R.id.user_id);
        }
    }
}
