package studio.mon.callhistoryanalyzer.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

/**
 * Created by admin on 6/4/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context, "callanalyzer", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = " CREATE TABLE tblCall( "+
                " id integer primary key autoincrement, " +
                " name text, " +
                " number text, " +
                " time text, " +
                " type text, " +
                " duration text " +
                ")";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion != oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS tblCall");
            onCreate(db);
        }
    }
    public int getCount() {
        String countQuery = "SELECT * FROM tblCall";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public List<CallAnalyzer> getAll() {
        List<CallAnalyzer> callAnalyzerList = new ArrayList<CallAnalyzer>();

        String selectQuery = "SELECT  * FROM tblCall;" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CallAnalyzer callAnalyzer = new CallAnalyzer();
                callAnalyzer.setId(Integer.parseInt(cursor.getString(0)));
                callAnalyzer.setName(cursor.getString(1));
                callAnalyzer.setNumber(cursor.getString(2));
                callAnalyzer.setTime(cursor.getString(3));
                callAnalyzer.setType(cursor.getString(4));
                callAnalyzer.setDuration(cursor.getString(5));
                callAnalyzerList.add(callAnalyzer);
            } while (cursor.moveToNext());
        }

        return callAnalyzerList;
    }

    public void add(CallAnalyzer callAnalyzer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", callAnalyzer.getName());
        values.put("number", callAnalyzer.getNumber());
        values.put("time", callAnalyzer.getTime());
        values.put("type", callAnalyzer.getType());
        values.put("duration", callAnalyzer.getDuration());
        db.insert("tblCall", null, values);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS tblCall");
        onCreate(db);
    }
}
