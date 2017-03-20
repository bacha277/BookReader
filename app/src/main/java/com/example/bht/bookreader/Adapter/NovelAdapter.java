package com.example.bht.bookreader.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bht.bookreader.DBHelper.DBHelper;
import com.example.bht.bookreader.R;
import com.squareup.picasso.Picasso;

/**
 * Created by bht on 3/17/17.
 */

public class NovelAdapter extends CursorAdapter {
    public NovelAdapter(Context context, Cursor c) {
        super(context, c, false);
    }
    class ViewHolder
    {
        ImageView img;
        TextView txt;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.list_novel_item,parent,false);
        ViewHolder holder=new ViewHolder();
        holder.img=(ImageView) v.findViewById(R.id.img);
        holder.txt=(TextView) v.findViewById(R.id.txtTitle);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder=(ViewHolder)view.getTag();
        int id = context.getResources().getIdentifier(context.getPackageName()+":drawable/" + cursor.getString(cursor.getColumnIndex(DBHelper.NOVEL_IMG_COLUMN)), null, null);
        Picasso.with(context).load(id).into(holder.img);
        holder.txt.setText(cursor.getString(cursor.getColumnIndex(DBHelper.NOVEL_TITLE_COLUMN)));
    }
}
