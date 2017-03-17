package com.example.bht.bookreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bht.bookreader.Adapter.ChapterAdapter;
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
        openOptionsMenu();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.menuSearch);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Cursor c = myDbHelper.searchChapterByKeyword(query);
                Intent intent = new Intent(MainActivity.this,SearchChapterActivity.class);
                intent.putExtra("keyword",query);
                startActivityForResult(intent,0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
