package com.notes.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.notes.R;
import com.notes.adapter.RecyclerViewAdapter;
import com.notes.database.NoteDatabase;
import com.notes.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class FrontScreenActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    private RecyclerView recyclerView;
    private List<Note> noteList;
    private RecyclerViewAdapter recyclerViewAdapter;
    public static String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R .layout.activity_front_screen);

        ImageView addImage = findViewById(R.id.addImageButton);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class),
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });
        noteList = new ArrayList<>();
        Log.d("notelist", noteList.toString());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );

        recyclerViewAdapter = new RecyclerViewAdapter(noteList);
        recyclerView.setAdapter(recyclerViewAdapter);

        getAllNotes();
        Log.d(TAG, "onCreate: ");

    }


    void getAllNotes(){

//        final Note note = new Note();
        @SuppressLint("StaticFieldLeak")
        class GetAllNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);

                if(noteList.size() == 0){
                    noteList.addAll(notes);

                    recyclerViewAdapter.notifyDataSetChanged();
                    Log.d("TAG", "onPostExecute: ");
                }
                else {
                    noteList.add(0, notes.get(0));

                    recyclerViewAdapter.notifyItemInserted(0);
                    Log.d(TAG, "onPostExecute: ");
                }
                recyclerView.smoothScrollToPosition(0);


            }
        }
        new GetAllNotesTask().execute();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getAllNotes();
        Log.d(TAG, "onActivityResult: ");
    }
}