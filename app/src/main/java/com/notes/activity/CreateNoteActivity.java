package com.notes.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.notes.R;
import com.notes.database.NoteDatabase;
import com.notes.entity.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    EditText title, subTitle, noteText;
    ImageView saveButton, backButton;
    TextView dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.title);
        subTitle = (EditText) findViewById(R.id.subtitle);
        noteText = (EditText) findViewById(R.id.noteText);
        dateTime = findViewById(R.id.textViewDate);

        saveButton = findViewById(R.id.saveButton);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        dateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault())
                .format(new Date())
        );


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });



    }

    void saveNote(){
        if(title.getText().toString().isEmpty()){
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "title:");
            return;
        } else if (subTitle.getText().toString().isEmpty() && noteText.getText().toString().isEmpty()) {

            Toast.makeText(this, "Subtitle and note both cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }


            final Note note = new Note();

            note.setTitle(title.getText().toString());
            note.setSubTitle(subTitle.getText().toString());
            note.setNoteText(noteText.getText().toString());
            note.setDateTime(dateTime.getText().toString());

            @SuppressLint("StaticFieldLeak")
            class SaveNoteClass extends AsyncTask<Void, Void, Void> {


                @Override
                protected Void doInBackground(Void... voids) {
                    NoteDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                    Log.d("TAG", "doInBackground: CreateNoteActivity");

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
            new SaveNoteClass().execute();

//            kjfsldjfsjfl










        }


    }


