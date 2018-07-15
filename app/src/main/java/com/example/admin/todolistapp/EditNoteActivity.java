package com.example.admin.todolistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    String noteText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_field);
        Intent intent = getIntent();
        noteText = intent.getStringExtra(Intent_Constants.INTENT_EDITNOTE_DATA);
        position = intent.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION, -1);
        EditText noteData = findViewById(R.id.note);
        noteData.setText(noteText);
    }

    public void saveButtonClicked (View v) {
        String editedNoteText = ((EditText)findViewById(R.id.note)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Intent_Constants.INTENT_EDITED_NOTE, editedNoteText);
        intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
        setResult(Intent_Constants.INTENT_RESULT_CODE_TWO, intent);
        finish();
    }

}
