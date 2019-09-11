package com.example.kasir;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PembelianActivity extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    TextView txtNamaBarang, txtHarga, txtstok, txtjumlahHarga;
    EditText edtPembelian;
    Button btnBeli, btnOke;
    int stokLama, harganya, total;
    int idnya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);

        dbHelper = new DataHelper(this);
        txtNamaBarang = (TextView) findViewById(R.id.text_namaBarang);
        txtHarga = (TextView) findViewById(R.id.text_harga);
        txtstok = (TextView) findViewById(R.id.text_stok);
        txtjumlahHarga = (TextView) findViewById(R.id.txtjumlahharga);
        edtPembelian = (EditText) findViewById(R.id.textView3);
        btnBeli = (Button) findViewById(R.id.btn_Beli);
        btnOke = (Button) findViewById(R.id.btn_Oke);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT*FROM kasir WHERE nama_barang='" +
                getIntent().getStringExtra("Nama") + "'", null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            idnya = cursor.getInt(0);
            stokLama = cursor.getInt(4);
            harganya = cursor.getInt(3);
            txtHarga.setText(Integer.toString(harganya));
            txtstok.setText(Integer.toString(stokLama));
            txtNamaBarang.setText(cursor.getString(1));


        }

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total=harganya*Integer.parseInt(edtPembelian.getText().toString());
                String totalnya=Integer.toString(total);
                txtjumlahHarga.setText(totalnya);

            }
        });

        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                        int jmlbeli=Integer.parseInt(edtPembelian.getText().toString());
                        int stokBaru=stokLama-jmlbeli;

                        db.execSQL("update kasir set stok='"+ stokBaru+"'where id='"+idnya+"'");
                finish();
            }
        });

    }

}
