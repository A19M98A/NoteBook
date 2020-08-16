package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LNote extends AppCompatActivity {

    public ArrayAdapter adapter;
    DBHelper db;
    Button Add, Search;
    EditText SearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_note);

        G.currentActivity = this;

        db = new DBHelper(this);

        ListView lstContent = (ListView) findViewById(R.id.resultlist);
        adapter = new AdapterNode(G.nodes);
        lstContent.setAdapter(adapter);

//        adapter.notifyDataSetChanged();
        if (adapter != null)
            adapter.notifyDataSetChanged();

        Add = (Button) findViewById(R.id.add);
        Search = (Button) findViewById(R.id.search);

        SearchText = (EditText) findViewById(R.id.text);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LNote.this, AddNote.class);
                LNote.this.startActivity(intent);
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.getData(SearchText.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void onResume() {
        G.currentActivity = this;
        super.onResume();
    }
}