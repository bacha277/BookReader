package com.example.bht.bookreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bht.bookreader.Adapter.ChapterAdapter;
import com.example.bht.bookreader.DBHelper.DBHelper;
import com.example.bht.bookreader.Model.Chapter;

import java.io.IOException;

public class ChapterDetailActivity extends AppCompatActivity {
    private DBHelper myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);

        Intent intent=getIntent();

        initDbHelper();

        Chapter chap = myDbHelper.getChapterById(intent.getIntExtra("chapterId", 0));

        TextView txtContent=(TextView)findViewById(R.id.txtContent);

        txtContent.setText(chap.getContent());
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
