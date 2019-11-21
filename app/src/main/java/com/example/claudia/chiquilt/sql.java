package com.example.claudia.chiquilt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sql extends SQLiteOpenHelper {

        private static final String DATABASE = "CONTACTOS";
        private static final int VERSION=1;

        private final String tprod="CREATE TABLE PERSONAS( ID_PERSONA INTEGER PRIMARY KEY, NOMBRE TEXT NOT NULL, " +
                "ORGANIZACION TEXT NOT NULL, CUMPLEANIOS TEXT NOT NULL, SEXO TEXT NOT NULL, TELEFONO TEXT NOT NULL, FOTO TEXT NOT NULL)";

    public sql(Context context){
            super(context, DATABASE, null,VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(tprod);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(newVersion>oldVersion){
                db.execSQL("DROP TABLE IF EXISTS PERSONAS");
                db.execSQL(tprod);
            }
        }
    }
