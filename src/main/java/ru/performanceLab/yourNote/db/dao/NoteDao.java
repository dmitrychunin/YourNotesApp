package ru.performanceLab.yourNote.db.dao;

import ru.performanceLab.yourNote.db.model.Note;
import ru.performanceLab.yourNote.db.model.User;
import ru.performanceLab.yourNote.db.dao.cache.CachedResult;

import java.util.List;

public interface NoteDao {
    List<Note> findNotesByUser(User user);

    Note createEmptyNote(String userName, String noteName);

    void deleteNote(Note note);

    void updateNote(Note note, String text);

    @CachedResult
    Note getNote(String userName, String noteName);
}
