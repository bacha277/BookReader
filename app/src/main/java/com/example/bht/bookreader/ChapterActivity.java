package com.example.bht.bookreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bht.bookreader.Adapter.ChapterAdapter;
import com.example.bht.bookreader.DBHelper.DBHelper;
import com.example.bht.bookreader.Model.Chapter;

import java.io.IOException;

/**
 * Created by bht on 3/17/17.
 */

public class ChapterActivity extends AppCompatActivity {
    private DBHelper myDbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        Intent intent=getIntent();

        initDbHelper();

        Cursor c = myDbHelper.getChaptersByNovelId(intent.getIntExtra("novelId", 0));

        ChapterAdapter adapter=new ChapterAdapter(this,c);

        ListView lstChapter=(ListView)findViewById(R.id.lstChapter);

        lstChapter.setAdapter(adapter);

        lstChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChapterActivity.this,ChapterDetailActivity.class);
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
