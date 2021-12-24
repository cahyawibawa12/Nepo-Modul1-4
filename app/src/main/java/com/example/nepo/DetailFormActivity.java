package com.example.nepo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import Database.DbHelper;
import Database.FormHandler;

public class DetailFormActivity extends AppCompatActivity {

    TextView NamaLengkap, Umur, Kegiatan;
    EditText TanggalLahir;
    Button btnEdit, btnHapus;
    SeekBar seekBar;
    RadioButton radioButton, rbperempuan,rblaki;
    RadioGroup rgroup;
    CheckBox keamanan, dokumentasi, acara, rohani, perlengkapan, kesekre;
    DatePickerDialog.OnDateSetListener setListener;
    String nilaiUmur, gender, pengalaman;
    String result;
    private int id = 1;
    private ArrayList<FormHandler> listformHandler = new ArrayList<FormHandler>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailform);

        NamaLengkap = findViewById(R.id.nama_edited);
        TanggalLahir = findViewById(R.id.tanggal_lahir_edited);
        Kegiatan = findViewById(R.id.panitia_edited);
        Umur = findViewById(R.id.umur_edit);
        seekBar = findViewById(R.id.seekBar_umur);
        rgroup = findViewById(R.id.rgrup_kelamin);
        rblaki = findViewById(R.id.radioButton_laki);
        rbperempuan = findViewById(R.id.radioButton2_perem);
        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);
        keamanan = findViewById(R.id.checkBox1_edit);
        dokumentasi = findViewById(R.id.checkBox2_edit);
        acara = findViewById(R.id.checkBox3_edit);
        rohani = findViewById(R.id.checkBox4_edit);
        perlengkapan = findViewById(R.id.checkBox5_edit);
        kesekre = findViewById(R.id.checkBox6_edit);
        result = "";

        Intent getData = getIntent();
        id = getData.getIntExtra("id", 1);

        if (id > 0) {
            final DbHelper dh = new DbHelper(DetailFormActivity.this);
            Cursor cursor = dh.detailForm(id);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                while (!cursor.isAfterLast()) {
                    NamaLengkap.setText((cursor.getString(cursor.getColumnIndexOrThrow("nama"))));
                    TanggalLahir.setText((cursor.getString(cursor.getColumnIndexOrThrow("tanggal_lahir"))));
                    Umur.setText((cursor.getString(cursor.getColumnIndexOrThrow("umur"))));
//                    Log.d("AAAAA", ((cursor.getString(cursor.getColumnIndexOrThrow("jenis_kelamin")))));
                    if(((cursor.getString(cursor.getColumnIndexOrThrow("jenis_kelamin")))).equals("Laki - Laki")){
                        rblaki.setChecked(true);
                    }else if(((cursor.getString(cursor.getColumnIndexOrThrow("jenis_kelamin")))).equals("Perempuan")){
                        rbperempuan.setChecked(true);
                    }
                    Kegiatan.setText((cursor.getString(cursor.getColumnIndexOrThrow("kegiatan"))));
                    result=((cursor.getString(cursor.getColumnIndexOrThrow("pengalaman"))));

                    if (result.contains("Keamanan")){
                        keamanan.setChecked(true);
                    }
                    if (result.contains("Dokumentasi")){
                        dokumentasi.setChecked(true);
                    }
                    if (result.contains("Acara")){
                        acara.setChecked(true);
                    }
                    if (result.contains("Rohani")){
                        rohani.setChecked(true);
                    }
                    if (result.contains("Perlengkapan")){
                        perlengkapan.setChecked(true);
                    }
                    if (result.contains("Kesekre")){
                        kesekre.setChecked(true);
                    }

                    cursor.moveToNext();
                }
//                dh.close();
            }
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        TanggalLahir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DetailFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        TanggalLahir.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                nilaiUmur = String.valueOf(i);
                Umur.setText("Umur : " + nilaiUmur);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = getGenderSelected();
                pengalaman = getServiceSelected();

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailFormActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("Submit");
                builder.setMessage(
                        "Apakah Anda Sudah Yakin Dengan Data Anda ?\n\n" +
                                "Nama Lengkap : \n" + NamaLengkap.getText() + "\n\n" +
                                "Tanggal Lahir : \n" + TanggalLahir.getText() + "\n\n" +
                                "Umur : \n" + nilaiUmur + "\n\n" +
                                "Jenis Kelamin : \n" + gender + "\n\n" +
                                "Nama Lengkap : \n" + Kegiatan.getText() + "\n\n" +
                                "Pengalaman : \n" + pengalaman + ""
                );

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() { //method button positive desicion
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbHelper dbHelper = new DbHelper(getApplicationContext());
                        FormHandler listformHandler = new FormHandler();
                        listformHandler.setNama(NamaLengkap.getText().toString());
                        listformHandler.setTanggal_lahir(TanggalLahir.getText().toString());
                        listformHandler.setUmur(nilaiUmur);
                        listformHandler.setJenis_kelamin(gender);
                        listformHandler.setKegiatan(Kegiatan.getText().toString());
                        listformHandler.setPengalaman(pengalaman);

                        boolean suntingForm = dbHelper.suntingForm(listformHandler, id);

                        if (suntingForm) {
                            Toast.makeText(DetailFormActivity.this, "Data anda berhasil terdaftarkan !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailFormActivity.this, "Data anda gagal terdaftar!", Toast.LENGTH_SHORT);
                        }
//                        dbHelper.close();
                        Intent goListForm = new Intent(DetailFormActivity.this,ListFormActivity.class);
                        startActivity(goListForm);

//                        NamaLengkap.setText("");
//                        TanggalLahir.setText("");
//                        radioButton.setText("");
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() { //method button negative desicion
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create(); //method get alert dan create alert
                alertDialog.show(); //to show alert
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailFormActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("Hapus Data");
                builder.setMessage("Yakin menghapus data?\n");

                        builder.setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DbHelper dbHelper = new DbHelper(getApplicationContext());

                                boolean hapusForm = dbHelper.hapusForm(id);

                                if (hapusForm) {
                                    Toast.makeText(DetailFormActivity.this, "Hapus Peminjaman Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DetailFormActivity.this, "Hapus Peminjaman Gagal", Toast.LENGTH_SHORT).show();
                                }
//                                dbHelper.close();
                                Intent goListForm = new Intent(DetailFormActivity.this,ListFormActivity.class);
                                startActivity(goListForm);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create(); //method get alert dan create alert
                alertDialog.show(); //to show alert
            }
        });
    }

    public String getServiceSelected(){
        result = "";
        //checkbox
        if(keamanan.isChecked()){
            result += keamanan.getText().toString()+"\n";
        }
        if(dokumentasi.isChecked()){
            result += dokumentasi.getText().toString()+"\n";
        }
        if(acara.isChecked()){
            result += acara.getText().toString()+"\n";
        }
        if(rohani.isChecked()){
            result += rohani.getText().toString()+"\n";
        }
        if(perlengkapan.isChecked()){
            result += perlengkapan.getText().toString()+"\n";
        }
        if(kesekre.isChecked()){
            result += kesekre.getText().toString()+"\n";
        }
        return result;
    }

    private String getGenderSelected(){
        String gender = "";

        int selectedId = rgroup.getCheckedRadioButtonId();

        if (selectedId == rblaki.getId()){
            gender = "Laki-Laki";
        }
        else if (selectedId == rbperempuan.getId()){
            gender = "Perempuan";
        }

        return gender;
    }
}