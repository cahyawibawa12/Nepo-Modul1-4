package com.example.nepo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import java.util.Calendar;

import Database.DbHelper;
import Database.FormHandler;

public class FormActivity extends AppCompatActivity {
    TextView NamaLengkap, Umur, Kegiatan;
    EditText TanggalLahir;
    Button btnSubmit;
    SeekBar seekBar;
//    RadioButton radioButton;
    RadioButton rblaki, rbperempuan;
    RadioGroup rgroup;
    CheckBox keamanan, dokumentasi, acara, rohani, perlengkapan, kesekre;
    DatePickerDialog.OnDateSetListener setListener;
    String nilaiUmur, gender, pengalaman;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        NamaLengkap = findViewById(R.id.edit_nama);
        TanggalLahir = findViewById(R.id.edit_tanggal_lahir);
        Kegiatan = findViewById(R.id.edit_panitia);
        Umur = findViewById(R.id.umur);
        seekBar = findViewById(R.id.seekBar);
        rgroup = findViewById(R.id.rgrup);
        rblaki = findViewById(R.id.laki);
        rbperempuan = findViewById(R.id.perem);
        btnSubmit = findViewById(R.id.btnSubmit);
        keamanan = findViewById(R.id.checkBox1);
        dokumentasi = findViewById(R.id.checkBox2);
        acara = findViewById(R.id.checkBox3);
        rohani = findViewById(R.id.checkBox4);
        perlengkapan = findViewById(R.id.checkBox5);
        kesekre = findViewById(R.id.checkBox6);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        TanggalLahir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = getGenderSelected();
                pengalaman = getServiceSelected();

                AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("Submit");
                builder.setMessage(
                        "Apakah Anda Sudah Yakin Dengan Data Anda ?\n\n"+
                                "Nama Lengkap : \n" + NamaLengkap.getText() + "\n\n"+
                                "Tanggal Lahir : \n" + TanggalLahir.getText() + "\n\n"+
                                "Umur : \n" + nilaiUmur + "\n\n"+
                                "Jenis Kelamin : \n" + gender + "\n\n"+
                                "Nama Lengkap : \n" + Kegiatan.getText() + "\n\n"+
                                "Pengalaman : \n" + pengalaman + ""
                );

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() { //method button positive desicion


                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbHelper dbHelper = new DbHelper(getApplicationContext());
                        FormHandler formHandler = new FormHandler();
                        formHandler.setNama(NamaLengkap.getText().toString());
                        formHandler.setTanggal_lahir(TanggalLahir.getText().toString());
                        formHandler.setUmur(nilaiUmur);
                        formHandler.setJenis_kelamin(gender);
                        formHandler.setKegiatan(Kegiatan.getText().toString());
                        formHandler.setPengalaman(pengalaman);

                        boolean tambahForm = dbHelper.tambahForm(formHandler);

                        if(tambahForm) {
                            Toast.makeText(FormActivity.this, "Data anda berhasil terdaftarkan !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FormActivity.this, "Data anda gagal terdaftar!", Toast.LENGTH_SHORT);
                        }
//                        dbHelper.close();

//                        NamaLengkap.setText("");
//                        TanggalLahir.setText("");
//                        gender.setText("");
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
