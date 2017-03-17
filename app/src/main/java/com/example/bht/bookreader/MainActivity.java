package com.example.bht.bookreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        ListView lst=(ListView)findViewById(R.id.lstNovel);
        Cursor c=myDbHelper.getAllNovels();
        final NovelAdapter adapter=new NovelAdapter(this,c);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ChapterActivity.class);
                intent.putExtra("novelId",(int)id);
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
