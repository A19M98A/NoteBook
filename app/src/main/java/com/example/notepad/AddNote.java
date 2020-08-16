package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNote extends AppCompatActivity {

    Button Save;
    EditText Title, Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Save = (Button) findViewById(R.id.save);
        Title = (EditText) findViewById(R.id.title);
        Text = (EditText) findViewById(R.id.text);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes m1 = new Notes();
                G.database.insertNote(Title.getText().toString(), Text.getText().toString());
                G.UpdateNotes();
                finish();
            }
        });
    }
}