package com.example.kasir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private  static final String DATABASE_NAME="Kasir";
    private  static final int DATABASE_VERSION=1;

    public DataHelper(Context context){super(context,DATABASE_NAME,null,DATABASE_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table kasir (id intager primarykey,nama_Barang text null,supplier text null,harga integer ,stok integer );";
        Log.d("Data","onCreate:"+sql);
        db.execSQL(sql);
        sql="INSERT INTO kasir(id,nama_Barang,supplier,harga,stok)VALUES(2,'jellyDrinkBig','PT.Wingsfood',2000,100);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
