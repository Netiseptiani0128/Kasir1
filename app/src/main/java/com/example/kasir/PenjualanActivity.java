package com.example.kasir;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PenjualanActivity extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    int stokLama, harganya1,total1, idnya;
    TextView namaBarang,harga,jumlahHarga,suplier;
    EditText jumlahPembelian0;
    Button jual,ya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        dbHelper = new DataHelper(this);
        namaBarang = (TextView) findViewById(R.id.barang);
        harga = (TextView) findViewById(R.id.harga);
        jumlahHarga = (TextView) findViewById(R.id.jumlah);
        jumlahPembelian0 = (EditText) findViewById(R.id.edtPembelian);
        suplier=(TextView)findViewById(R.id.suplier);
        jual = (Button) findViewById(R.id.button2);
        ya = (Button) findViewById(R.id.oke1);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM kasir WHERE nama_barang='" +
                getIntent().getStringExtra("Nama") + "'", null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            idnya = cursor.getInt(0);
            stokLama = cursor.getInt(4);
            harganya1 = cursor.getInt(3);
            suplier.setText(cursor.getString(2));
            harga.setText(Integer.toString(harganya1));
            namaBarang.setText(cursor.getString(1));
        }
        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total1=harganya1*Integer.parseInt(jumlahPembelian0.getText().toString());
                String totalnya=Integer.toString(total1);
                jumlahHarga.setText(totalnya);
            }
        });
        jual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                int jmlbeli=Integer.parseInt(jumlahPembelian0.getText().toString());
                int stokbaru=stokLama+jmlbeli;

                db.execSQL("update kasir set stok='"+ stokbaru+"'where id='"+idnya+"'");
                finish();
            }
        });
    }
}
