package ru.performanceLab.yourNote.service.dao;

import ru.performanceLab.yourNote.model.Note;
import ru.performanceLab.yourNote.model.User;

import java.util.List;

public interface NoteDao {
    List<Note> findNotesByUser(User user);

    Note createEmptyNote(String userName, String noteName);

    void deleteNote(Note note);

    void updateNote(Note note, String text);

    @CachedResult
    Note getNote(String userName, String noteName);
}
