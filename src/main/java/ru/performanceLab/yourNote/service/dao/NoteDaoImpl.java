package ru.performanceLab.yourNote.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.performanceLab.yourNote.model.Note;
import ru.performanceLab.yourNote.model.User;
import ru.performanceLab.yourNote.repository.NoteRepository;
import ru.performanceLab.yourNote.repository.UserRepository;

import java.util.List;

@Service
public class NoteDaoImpl implements NoteDao {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Note> findNotesByUser(User user) {
        return noteRepository.findNotesByUser(user);
    }

    @Override
    public Note createEmptyNote(String userName, String noteName) {
        User user = userRepository.findByName(userName);
        Note note = new Note(user, noteName, "");
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Note note) {
        noteRepository.delete(note.getId());
    }

    @Override
    public void updateNote(Note note, String text) {
        note.setNoteText(text);
        noteRepository.save(note);
    }

    /*TODO: add key types and count definitions*/
    @CachedResult
    @Override
    public Note getNote(String userName, String noteName) {
        User user = userRepository.findByName(userName);
        Note note = noteRepository.findNoteByUserAndName(user, noteName);
        return note;
    }
}
