package com.example.bht.bookreader.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bht.bookreader.DBHelper.DBHelper;
import com.example.bht.bookreader.R;

/**
 * Created by bht on 3/17/17.
 */

public class ChapterAdapter extends CursorAdapter {
    public ChapterAdapter(Context context, Cursor c) {
        super(context, c, false);
    }
    class ViewHolder
    {
        TextView txtDescription;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.list_chapter_item,parent,false);
        ViewHolder holder=new ViewHolder();
        holder.txtDescription=(TextView) v.findViewById(R.id.txtDescription);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder=(ViewHolder)view.getTag();
        int number=cursor.getInt(cursor.getColumnIndex(DBHelper.CHAPTER_NUMBER_COLUMN));
        String description=cursor.getString(cursor.getColumnIndex(DBHelper.CHAPTER_DESCRIPTION_COLUMN));
        holder.txtDescription.setText("Chapter "+number+" : "+description);
    }
}
