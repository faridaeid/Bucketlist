package edu.aucegypt.bucketlist;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class add_new_post extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener{

    private Spinner spinner;
    private TextView txt_debug;
    private EditText text;
    private Button submit;
    private int categ_selected;
    private String[] categories = {"Travel", "Food", "Sport", "Music"};
    private SharedPreferences shPref;

    public add_new_post() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_new_post, container, false);

        spinner = (Spinner)view.findViewById(R.id.categories);
        txt_debug = (TextView)view.findViewById(R.id.debugtextview);
        text = (EditText)view.findViewById(R.id.task_descr);
        submit = (Button)view.findViewById(R.id.post);
        submit.setOnClickListener(this);

        shPref = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, categories);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {categ_selected = position;}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        String post_text = text.getText().toString();
        if(!post_text.equals("") && v.getId() == R.id.post)
        {
            // get date
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
            String currentDate = sdf.format(date);

            Firebase ref = new Firebase("https://bucketlist-15d3c.firebaseio.com/");
            Firebase userRef = ref.child("Posts").push();

            Post my_new_post = new Post(post_text,
                    shPref.getString("userID", ""),
                    categories[categ_selected],
                    "",
                    currentDate);

            userRef.setValue(my_new_post);
        }
    }
}
