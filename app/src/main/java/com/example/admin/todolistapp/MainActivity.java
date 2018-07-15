package com.example.admin.todolistapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String noteText;
    int position;
    ViewGroup mainContainer;
    TheColorState theColorNum = TheColorState.white;// true or false

    enum TheColorState {
        white, cyan
    }

    final String fileName = "Todo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        registerForContextMenu(listView);
        mainContainer = findViewById(R.id.mainContainer);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditNoteActivity.class);
                intent.putExtra(Intent_Constants.INTENT_EDITNOTE_DATA, arrayList.get(position).toString());
                intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE_TWO);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int _position, long id) {
                position = _position;
                return false;
            }
        });

        try {
            Scanner sc = new Scanner(openFileInput(fileName)); //reading
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                arrayAdapter.add(data);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveToTextFile() {
        try {
            PrintWriter pw = new PrintWriter(openFileOutput(fileName, Context.MODE_PRIVATE));//writing
            for (String data : arrayList) {
                pw.println(data);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView) {
            getMenuInflater().inflate(R.menu.option_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_option:
                noteText = arrayList.get(position);
                arrayList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                saveToTextFile();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    //to change the colour of the background
    private void toggleColor() {
        if (theColorNum == TheColorState.white) {
            mainContainer.setBackgroundColor(Color.CYAN);
            theColorNum = TheColorState.cyan;
        } else if (theColorNum == TheColorState.cyan) {
            mainContainer.setBackgroundColor(Color.WHITE);
            theColorNum = TheColorState.white;
        }
    }

    public void onClick2(View view) {
        toggleColor();
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AddNoteActivity.class);
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Intent_Constants.INTENT_REQUEST_CODE) {
            noteText = data.getStringExtra(Intent_Constants.INTENT_NOTE_FIELD);
            arrayList.add(noteText);
            arrayAdapter.notifyDataSetChanged();
            saveToTextFile();   //this is to save the data after exit the app
        } else if (resultCode == Intent_Constants.INTENT_REQUEST_CODE_TWO) {
            noteText = data.getStringExtra(Intent_Constants.INTENT_EDITED_NOTE);
            position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION, -1);
            arrayList.remove(position);
            arrayList.add(position, noteText);
            arrayAdapter.notifyDataSetChanged();
            saveToTextFile();   //this is to save the data after exit the app
        }
    }


}
