package com.example.virtualstudyroom.service;

import com.example.virtualstudyroom.model.Note;
import com.example.virtualstudyroom.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public void addNewNote(Note note) {
        // Add validation if necessary
        noteRepository.save(note);
    }
}
