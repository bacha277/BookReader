package com.example.bht.bookreader.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bht.bookreader.Model.Chapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by bht on 3/16/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_PATH = null;
    private static String DB_NAME = "novelDB";
    public static final String NOVEL_TABLE_NAME="Novel";
    public static final String NOVEL_ID_COLUMN="_id";
    public static final String NOVEL_TITLE_COLUMN="title";
    public static final String NOVEL_IMG_COLUMN="img";
    public static final String CHAPTER_TABLE_NAME="Chapter";
    public static final String CHAPTER_ID_COLUMN="_id";
    public static final String CHAPTER_NUMBER_COLUMN="number";
    public static final String CHAPTER_DESCRIPTION_COLUMN="description";
    public static final String CHAPTER_CONTENT_COLUMN="content";
    public static final String CHAPTER_NOVEL_ID_COLUMN="novel_id";
    private SQLiteDatabase myDB;
    private final Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        myContext = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

    public void createDataBase() throws IOException {


        if (!checkDataBase()) {

            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDB != null)
            myDB.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAllNovels() {
        Cursor c = myDB.rawQuery("SELECT * FROM " + NOVEL_TABLE_NAME, null);
        if (c!=null)
            c.moveToFirst();
        return c;
    }

    public Cursor getChaptersByNovelId(int novelId) {
        Cursor c = myDB.rawQuery("SELECT * FROM " + CHAPTER_TABLE_NAME + " WHERE " + CHAPTER_NOVEL_ID_COLUMN + "=" + novelId,null);
        if (c!=null)
            c.moveToFirst();
        return c;
    }

    public Chapter getChapterById (int id) {
        Cursor c=myDB.query(CHAPTER_TABLE_NAME,new String[]{CHAPTER_ID_COLUMN,CHAPTER_CONTENT_COLUMN,CHAPTER_NOVEL_ID_COLUMN,CHAPTER_DESCRIPTION_COLUMN,CHAPTER_NUMBER_COLUMN},CHAPTER_ID_COLUMN+"=?",new String[]{id+""},null,null,null);
        if (c!=null)
            c.moveToFirst();
        Chapter chap=new Chapter();
        chap.setId(c.getInt(c.getColumnIndex(CHAPTER_ID_COLUMN)));
        chap.setContent(c.getString(c.getColumnIndex(CHAPTER_CONTENT_COLUMN)));
        chap.setDescription(c.getString(c.getColumnIndex(CHAPTER_DESCRIPTION_COLUMN)));
        chap.setNumber(c.getInt(c.getColumnIndex(CHAPTER_NUMBER_COLUMN)));
        chap.setNovelId(c.getInt(c.getColumnIndex(CHAPTER_NOVEL_ID_COLUMN)));
        return chap;
    }

    public Cursor searchChapterByKeyword(String keyword) {
        Cursor c = myDB.rawQuery("SELECT * FROM " + CHAPTER_TABLE_NAME + " WHERE " + CHAPTER_CONTENT_COLUMN + " LIKE '%" + keyword + "%'",null);
        if (c!=null)
            c.moveToFirst();
        return c;
    }
}
