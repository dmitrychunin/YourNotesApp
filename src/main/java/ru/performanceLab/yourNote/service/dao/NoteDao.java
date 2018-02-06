package ru.performanceLab.yourNote.service.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.performanceLab.yourNote.model.Note;
import ru.performanceLab.yourNote.model.User;
import ru.performanceLab.yourNote.repository.NoteRepository;
import ru.performanceLab.yourNote.repository.UserRepository;

import java.util.List;

@Service
public class NoteDao {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;

    private User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<Note> findNotesByUserId(Long id) {
        return noteRepository.findNotesByUserId(id);
    }

    public Note createEmptyNote(String userName, String noteName) {
        User user = userRepository.findByName(userName);
        Note note = new Note(user, noteName, "");
        return noteRepository.save(note);
    }

    public void deleteNote(String userName, String noteName) {
        noteRepository.delete(getNote(userName, noteName).getId());
    }

    public void updateNote(String userName, String noteName, String text) {
        Note note = getNote(userName, noteName);
        note.setNoteText(text);
        noteRepository.save(note);
    }

    private Note getNote(String userName, String noteName) {
        User user = userRepository.findByName(userName);
        Note note = noteRepository.findByUserIdAndNoteName(user.getId(), noteName);
        return note;
    }
}
