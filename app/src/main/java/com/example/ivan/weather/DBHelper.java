package com.example.ivan.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "BIG_TAG";

    public static final String TABLE_NAME = "cities";

    public static final String KEY_ID = "_id";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String SEASON = "season";
    public static final String FIRST_MONTH = "first_month";
    public static final String SECOND_MONTH = "second_month";
    public static final String THIRD_MONTH = "third_month";

    private static final String DATABASE_NAME = "db_weathers";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
                + KEY_ID + " integer not null primary key autoincrement, "
                + NAME + " text not null, "
                + TYPE + " text not null, "
                + SEASON + " text not null, "
                + FIRST_MONTH + " text not null, "
                + SECOND_MONTH + " text not null, "
                + THIRD_MONTH + " text not null" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }
}
