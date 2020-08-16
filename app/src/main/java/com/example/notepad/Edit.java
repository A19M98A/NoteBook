package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends AppCompatActivity {

    EditText Title, Text;
    Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle extras = getIntent().getExtras();
        int position = extras.getInt("POSITION");
        final Notes note = G.nodes.get(position);

        Title = (EditText) findViewById(R.id.title);
        Text = (EditText) findViewById(R.id.text);
        Save = (Button) findViewById(R.id.save);

        Title.setText(note.title);
        Text.setText(note.text);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.database.updateNote(note.id, Title.getText().toString(), Text.getText().toString());
                G.UpdateNotes();
                finish();
            }
        });
    }
}