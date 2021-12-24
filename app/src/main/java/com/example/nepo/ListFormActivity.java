package com.example.nepo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Database.DbHelper;
import Database.FormHandler;

public class ListFormActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    private DbHelper database;
    protected RecyclerView.Adapter listFormAdapter;
    private ArrayList<FormHandler> listformHandler = new ArrayList<FormHandler>();
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        database = new DbHelper(this);
        recyclerView = findViewById(R.id.recyclerview);
        btnAdd = findViewById(R.id.btn_add);

        final DbHelper dh = new DbHelper(getApplicationContext());
        Cursor cursor = dh.tampilForm();
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            while (!cursor.isAfterLast()){
                FormHandler listformHandlerList =new FormHandler();
                listformHandlerList.setId((cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
                listformHandlerList.setNama((cursor.getString(cursor.getColumnIndexOrThrow("nama"))));
                listformHandlerList.setTanggal_lahir((cursor.getString(cursor.getColumnIndexOrThrow("tanggal_lahir"))));
                listformHandlerList.setUmur((cursor.getString(cursor.getColumnIndexOrThrow("umur"))));
                listformHandlerList.setJenis_kelamin((cursor.getString(cursor.getColumnIndexOrThrow("jenis_kelamin"))));
                listformHandlerList.setKegiatan((cursor.getString(cursor.getColumnIndexOrThrow("kegiatan"))));
                listformHandlerList.setPengalaman((cursor.getString(cursor.getColumnIndexOrThrow("pengalaman"))));
                listformHandler.add(listformHandlerList);
                cursor.moveToNext();
            }
//            dh.close();
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListFormActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        listFormAdapter = new ListFormAdapter(listformHandler,ListFormActivity.this, recyclerView);
        recyclerView.setAdapter(listFormAdapter);
    }
}
