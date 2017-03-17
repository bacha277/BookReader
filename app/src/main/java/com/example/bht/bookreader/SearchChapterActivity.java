package com.example.bht.bookreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bht.bookreader.Adapter.ChapterAdapter;
import com.example.bht.bookreader.DBHelper.DBHelper;

import java.io.IOException;

public class SearchChapterActivity extends AppCompatActivity {
    private DBHelper myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        Intent intent=getIntent();

        initDbHelper();

        Cursor c = myDbHelper.searchChapterByKeyword(intent.getStringExtra("keyword"));

        ChapterAdapter adapter=new ChapterAdapter(this,c);

        ListView lstChapter=(ListView)findViewById(R.id.lstChapter);

        lstChapter.setAdapter(adapter);

        lstChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchChapterActivity.this,ChapterDetailActivity.class);
                intent.putExtra("chapterId",(int)id);
                startActivityForResult(intent,0);
            }
        });
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
