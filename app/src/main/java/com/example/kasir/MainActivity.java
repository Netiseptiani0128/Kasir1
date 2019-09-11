package com.example.kasir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    String[] tambah;
    DataHelper dbcenter;
    ListView listView;
    protected Cursor cursor;
    Button btnTambahBarang;
    public static MainActivity utama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTambahBarang=(Button)findViewById(R.id.btn_tambahBarang);

        btnTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buat = new Intent(MainActivity.this, TambahBarangActivity.class);
                startActivity(buat);
            }
        });
        utama = this;
        dbcenter = new DataHelper(this);
        RefreshList();
    }
    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT*FROM kasir",null);
        tambah= new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0;cc<cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            tambah[cc] = cursor.getString(1).toString();
        }
        listView=(ListView)findViewById(R.id.id_ListView);
        listView.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,tambah));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = tambah[arg2];//.getItemAtPosition(arg1).toString();
                final CharSequence[]dialogItem={"beli","jual","Hapus"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent jual = new Intent(getApplicationContext(),PembelianActivity.class);
                                jual.putExtra("Nama",selection);
                                startActivity(jual);
                                break;
                            case 1:
                                Intent beli = new Intent(getApplicationContext(),PenjualanActivity.class);
                                beli.putExtra("Nama",selection);
                                startActivity(beli);
                                break;
                            case 2:
                                SQLiteDatabase db= dbcenter.getWritableDatabase();
                                db.execSQL("Delete From kasir Where Nama='"+selection+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetInvalidated();
    }
}

