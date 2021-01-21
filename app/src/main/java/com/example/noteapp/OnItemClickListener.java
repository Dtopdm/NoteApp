package com.example.noteapp;

import com.example.noteapp.models.Note;

public interface OnItemClickListener {

    void onClick(int position);

    void onLongClick(int position);
}
