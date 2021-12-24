package com.example.nepo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Database.FormHandler;


public class ListFormAdapter extends RecyclerView.Adapter<ListFormAdapter.ViewHolder> {

    List<FormHandler> listformHandlerList;
    private Context context;
    private RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Nama, Tanggal_Lahir, Umur, Jenis_Kelamin,Kegiatan, Pengalaman;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nama = itemView.findViewById(R.id.isi_nama);
            Tanggal_Lahir = itemView.findViewById(R.id.isi_tanggal);
            Umur = itemView.findViewById(R.id.isi_umur);
            Jenis_Kelamin = itemView.findViewById(R.id.isi_gender);
            Kegiatan = itemView.findViewById(R.id.isi_kegiatan);
            Pengalaman = itemView.findViewById(R.id.isi_pengalaman);
            card = itemView.findViewById(R.id.cardList);
        }

    }

    public ListFormAdapter(List<FormHandler> listformHandlerList, Context context, RecyclerView recyclerView){
        this.listformHandlerList = listformHandlerList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ListFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_cardform, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListFormAdapter.ViewHolder holder, int position) {
        FormHandler listformHandler = listformHandlerList.get(position);
        Log.d("POS", String.valueOf(position));
        holder.Nama.setText(String.valueOf(listformHandler.getNama()));
        holder.Tanggal_Lahir.setText(String.valueOf(listformHandler.getTanggal_lahir()));
        holder.Umur.setText(String.valueOf(listformHandler.getUmur()));
        holder.Jenis_Kelamin.setText(String.valueOf(listformHandler.getJenis_kelamin()));
        holder.Kegiatan.setText(String.valueOf(listformHandler.getKegiatan()));
        holder.Pengalaman.setText(String.valueOf(listformHandler.getPengalaman()));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemId = listformHandlerList.get(holder.getAdapterPosition()).getId();
                Intent gotoDetail = new Intent(context, DetailFormActivity.class);
                gotoDetail.putExtra("id", itemId);
                Log.d("AAAAA", String.valueOf(listformHandlerList.get(holder.getAdapterPosition()).getId()));
                Log.d("AAAAA", String.valueOf(listformHandler.getId()));
                context.startActivity(gotoDetail);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listformHandlerList.size();
    }
}
