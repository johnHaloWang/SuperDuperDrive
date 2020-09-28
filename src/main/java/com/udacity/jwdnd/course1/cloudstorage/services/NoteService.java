package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "NoteService";

    @Autowired
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
    public Note getSingleNoteByNoteId(int noteId){
        return this.noteMapper.getItemByItemId(noteId);
    }
    public List<Note> getAllUserNotes(int userId){
        return this.noteMapper.getAll(userId);
    }

    public int editNoteByNoteObject(Note update){
        return this.noteMapper.update(update);
    }

    public int deleteNote(int noteId){
        return this.noteMapper.delete(noteId);
    }

    public int deleteAll() {return this.noteMapper.deleteAll();}
    public int addNote(Note add){
        return this.noteMapper.insert(add);
    }



}
