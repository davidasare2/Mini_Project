package com.example.miniproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class db extends SQLiteOpenHelper {
    private static final String name = "details";
    private static final int version = 1;

    private static final String userDetailsTable = "userDetails";
    private static final String email = "email";
    private static final String device = "device";
    private static final String deviceModel = "deviceModel";
    private static final String serialNumber = "serialNumber";
    public db(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS userDetailsTable ("
                +email+"TEXT,"+device+"TEXT,"+deviceModel+"TEXT,"
                +serialNumber+"TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS userDetailsTable ";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
    public SQLiteDatabase getDatabase(Context context) {
        try {
            db dbHelper = new db(context);
            return dbHelper.getWritableDatabase();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
