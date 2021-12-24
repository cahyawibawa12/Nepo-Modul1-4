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
import Database.KegiatanHandler;

public class OpenRecActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;
    private DbHelper database;
    protected RecyclerView.Adapter openRecAdapter;
    private ArrayList<KegiatanHandler> kegiatanHandler = new ArrayList<KegiatanHandler>();
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        database = new DbHelper(this);
        recyclerView = findViewById(R.id.recyclerview);
        btnAdd = findViewById(R.id.btn_add);

        final DbHelper dh = new DbHelper(getApplicationContext());
        Cursor cursor = dh.tampilKegiatan();
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            while (!cursor.isAfterLast()){
                KegiatanHandler kegiatanHandlerList =new KegiatanHandler();
                kegiatanHandlerList.setJudul((cursor.getString(cursor.getColumnIndexOrThrow("judul"))));
                kegiatanHandlerList.setDeskripsi((cursor.getString((cursor.getColumnIndexOrThrow("deskripsi")))));
                kegiatanHandler.add(kegiatanHandlerList);
                cursor.moveToNext();
            }
//            dh.close();
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenRecActivity.this, KegiatanActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        openRecAdapter = new OpenRecAdapter(kegiatanHandler,OpenRecActivity.this, recyclerView);
        recyclerView.setAdapter(openRecAdapter);
    }
}
