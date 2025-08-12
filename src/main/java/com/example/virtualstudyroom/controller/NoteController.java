package com.example.virtualstudyroom.controller;

import com.example.virtualstudyroom.model.Note;
import com.example.virtualstudyroom.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

    @PostMapping
    public void addNote(@RequestBody Note note) {
        noteService.addNewNote(note);
    }
}
