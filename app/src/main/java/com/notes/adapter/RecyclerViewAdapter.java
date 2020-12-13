package com.notes.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notes.R;
import com.notes.entity.Note;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NoteViewHolder>{
    List<Note> note ;
    public static String TAG;

    public RecyclerViewAdapter(List<Note> note) {
        this.note = note;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");


        return new NoteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote(note.get(position));
        Log.d(TAG, "onBindViewHolder: ");

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return note.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType: ");
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mSubtitle, mDatetime;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.titleItemView);
            mSubtitle = itemView.findViewById(R.id.subTitleItemView);
            mDatetime = itemView.findViewById(R.id.dateTimeItemView);
        }


        void setNote(Note note){
            Log.d(TAG, "NoteViewHolder setNote: ");
            if (note!= null) {
                mTitle.setText(note.getTitle());
                if(note.getSubTitle().isEmpty()){
                    mSubtitle.setVisibility(View.GONE);
                }
                else{
                    mSubtitle.setText(note.getSubTitle());
                }
                mDatetime.setText(note.getDateTime());
            }

        }
    }
}
