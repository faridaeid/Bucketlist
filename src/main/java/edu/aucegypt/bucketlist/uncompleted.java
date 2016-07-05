package edu.aucegypt.bucketlist;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class uncompleted extends Fragment implements ValueEventListener, AdapterView.OnItemClickListener{

    private SharedPreferences shPref;
    private TextView debug;
    private ListView list;
    private ArrayList<Post> uncompletedPosts;
    Context thisContext;
    private Firebase ref, postRef;

    public uncompleted() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_uncompleted, container, false);
        thisContext = container.getContext();

        // shared preference and views
        shPref = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        debug = (TextView)view.findViewById(R.id.txt_debug);
        list = (ListView)view.findViewById(R.id.list_uncomplete);
        list.setOnItemClickListener(this);

        ref = new Firebase("https://bucketlist-15d3c.firebaseio.com/");
        postRef = ref.child("Posts");

        postRef.addValueEventListener(this);
        return view;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> myIterable = dataSnapshot.getChildren();
        uncompletedPosts = new ArrayList<Post>();

        for (DataSnapshot data : myIterable)
        {
            Post myPost = data.getValue(Post.class);
            myPost.setKey(data.getKey());

            if(myPost.getPersonID().equals(shPref.getString("userID", ""))
                    && !myPost.getCompleted())      // get the 1) uncompleted tasks of the user only
                uncompletedPosts.add(myPost);
        }

        Incomplete_Adapter adapter = new Incomplete_Adapter(thisContext, uncompletedPosts);
        list.setAdapter(adapter);

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // get the key of the tsk you want to update
        String key = uncompletedPosts.get(position).getValueKey();
        Firebase selectedTask = postRef.child(key).child("completed");
        selectedTask.setValue(true);

        // updating the date as well
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String currentDate = sdf.format(date);
        selectedTask = postRef.child(key).child("date");
        selectedTask.setValue(currentDate);


    }
}
