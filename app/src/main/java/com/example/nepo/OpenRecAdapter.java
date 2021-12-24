package com.example.nepo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Database.KegiatanHandler;

public class OpenRecAdapter extends RecyclerView.Adapter<OpenRecAdapter.ViewHolder> {
    private List<KegiatanHandler> kegiatanHandlerList;
    private Context context;
    private RecyclerView recyclerView;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemJudul;
        TextView itemDeskripsi;

        public ViewHolder(@NonNull View itemView){
            super (itemView);
            itemJudul = itemView.findViewById(R.id.judul);
            itemDeskripsi = itemView.findViewById(R.id.isi_deskripsi);
        }
    }
    public OpenRecAdapter(List<KegiatanHandler> kegiatanHandlerList, Context context, RecyclerView recyclerView){
        this.kegiatanHandlerList = kegiatanHandlerList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    public OpenRecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card,parent,false);
        OpenRecAdapter.ViewHolder viewHolder = new OpenRecAdapter.ViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(@NonNull OpenRecAdapter.ViewHolder holder, int position) {
        KegiatanHandler kegiatanHandler = kegiatanHandlerList.get(position);
        holder.itemJudul.setText(String.valueOf(kegiatanHandler.getJudul()));
        holder.itemDeskripsi.setText(String.valueOf(kegiatanHandler.getDeskripsi()));
    }

    public long getItemId(int position) {
        return position;
    }

    public int getItemCount() {
        return kegiatanHandlerList.size();
    }
}
