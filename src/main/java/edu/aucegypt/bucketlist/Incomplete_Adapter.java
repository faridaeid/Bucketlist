package edu.aucegypt.bucketlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Farida on 05-Jul-16.
 */
public class Incomplete_Adapter extends ArrayAdapter<Post> {

    public Incomplete_Adapter(Context context, ArrayList<Post> posts)
    {
        super(context, 0,posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Post myPost = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.incomplete_item,parent,false);

        TextView categ = (TextView)convertView.findViewById(R.id.category);
        TextView task = (TextView)convertView.findViewById(R.id.task);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView likes = (TextView)convertView.findViewById(R.id.likes);

        categ.setText(myPost.getCategory());
        task.setText(myPost.getTask());
        date.setText(myPost.getDate());
        likes.setText(Integer.toString(myPost.getLikes()));

        return convertView;

    }
}
