package com.example.yeon.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class AddorEditEventActivity extends ActionBarActivity {

    TextView titleTextView;
    EditText EditTitleText;
    EditText EditNoteText;

    private String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_event);

        titleTextView = (TextView) findViewById(R.id.textViewTitle);
        EditTitleText = (EditText) findViewById(R.id.TitleText);
        EditNoteText = (EditText) findViewById(R.id.noteText);


        id = getIntent().getStringExtra("id");
        if (id != null) {
            titleTextView.setText("edit");
            EditTitleText.setText(getIntent().getStringExtra("title"));

        } else {
            titleTextView.setText("Add Calendar");
        }


    }

    public void onSave(View view) {
        String title = EditTitleText.getText().toString();
        String note =  EditNoteText.getText().toString();
        if (title.length() > 0) {
            Intent t = new Intent();
            if (id != null) {
                t.putExtra("id", id);
            }
            t.putExtra("title", title);
            if(note.length()>0){
                t.putExtra("note",note);
            }
            setResult(Activity.RESULT_OK,t);
        } else {
            setResult(Activity.RESULT_CANCELED);

        }
        finish();
    }

    public void onCancel(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }




}
