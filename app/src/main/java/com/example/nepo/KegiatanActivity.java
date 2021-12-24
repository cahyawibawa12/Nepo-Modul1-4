package com.example.nepo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DbHelper;
import Database.KegiatanHandler;

public class KegiatanActivity extends AppCompatActivity {

    private String judul_kegiatan, deskripsi_kegiatan;
    private EditText judul, deskripsi;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan);

        judul = (EditText)findViewById(R.id.edit_judul_kegiatan);
        deskripsi = (EditText)findViewById(R.id.edit_deskripsi);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul_kegiatan = judul.getText().toString();
                deskripsi_kegiatan = deskripsi.getText().toString();

                dialogAlert();
            }
        });
    }

    private void dialogAlert(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(KegiatanActivity.this);
        dialogAlertBuilder.setTitle("Konfirmasi Data");
        dialogAlertBuilder
                .setMessage("Judul : " +judul_kegiatan+ "\n" +
                        "Kategori : " +deskripsi_kegiatan+ "\n")
                .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbHelper dbHelper = new DbHelper(getApplicationContext());
                        KegiatanHandler bukuHandler = new KegiatanHandler();
                        bukuHandler.setJudul(judul_kegiatan.toUpperCase());
                        bukuHandler.setDeskripsi(deskripsi_kegiatan.toUpperCase());

                        boolean tambahKegiatan = dbHelper.tambahKegiatan(bukuHandler);

                        if (tambahKegiatan) {
                            Toast.makeText(KegiatanActivity.this, "Tambah Kegiatan Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(KegiatanActivity.this, "Tambah Kegiatan Gagal", Toast.LENGTH_SHORT).show();
                        }
//                        dbHelper.close();

                        judul.getText().clear();
                        deskripsi.getText().clear();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = dialogAlertBuilder.create();

        dialog.show();
    }
}