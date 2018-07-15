package com.example.admin.todolistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    // private static final String FILE_NAME = "to_do_list_app.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_field);
    }

    public void saveButtonClicked(View v) {
        String noteText = ((EditText) findViewById(R.id.note)).getText().toString();
        if (noteText.equals("")) {

        } else {
            Intent intent = new Intent();
            intent.putExtra(Intent_Constants.INTENT_NOTE_FIELD, noteText);
            setResult(Intent_Constants.INTENT_RESULT_CODE, intent);
            finish();
        }

        Toast.makeText(this, "Saved successfully.", Toast.LENGTH_LONG).show();
    }
}
