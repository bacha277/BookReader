package com.example.bht.bookreader;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bht.bookreader.Adapter.NovelAdapter;
import com.example.bht.bookreader.DBHelper.DBHelper;
import com.example.bht.bookreader.Model.Chapter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private DBHelper myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDbHelper();

//        Chapter chap = myDbHelper.getChapterById(4);
//        TextView txt=(TextView)findViewById(R.id.txt);
//        txt.setText(chap.getContent());
        ListView lst=(ListView)findViewById(R.id.lstNovel);
        Cursor c=myDbHelper.getAllNovels();
        NovelAdapter adapter=new NovelAdapter(this,c);
        lst.setAdapter(adapter);

    }

    private void initDbHelper() {
        myDbHelper=new DBHelper(this);
        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }
    }
}
