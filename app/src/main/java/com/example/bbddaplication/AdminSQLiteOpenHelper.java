package com.example.bbddaplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    //------------------------------Constructor--------------------------
    public AdminSQLiteOpenHelper(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //----------------------------Metodos Publicos-----------------------
    @Override
    public void onCreate(SQLiteDatabase bbdd) {
        bbdd.execSQL("CREATE TABLE articulos(codigo int primary key, nombre text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
