package com.example.nepo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOprec, btnDaftar, btnHelp;

        btnOprec = findViewById(R.id.button6);
        btnDaftar = findViewById(R.id.button7);
        btnHelp = findViewById(R.id.button8);

        btnOprec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OpenRecActivity.class);
                startActivity(intent);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListFormActivity.class);
                startActivity(intent);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }
}