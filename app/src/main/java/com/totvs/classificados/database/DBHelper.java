package com.totvs.classificados.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Totvs on 25/01/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shop.db";
    private static final int DATABASE_VERSION = 3;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MyStore.AdItemTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if(oldVersion == 1) {
            sqLiteDatabase.execSQL(
                    "ALTER TABLE " + MyStore.AdItemTable.TABLE_NAME +
                    " ADD COLUMN " + MyStore.AdItemTable.PRICE + " TEXT DEFAULT 0");
        }
        if(oldVersion == 2) {
            sqLiteDatabase.execSQL(
                    "ALTER TABLE " + MyStore.AdItemTable.TABLE_NAME +
                    " ADD COLUMN " + MyStore.AdItemTable.IMAGE + " TEXT DEFAULT ''");
        }

    }
}
