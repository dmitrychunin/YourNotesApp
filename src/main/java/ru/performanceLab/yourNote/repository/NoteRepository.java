package ru.performanceLab.yourNote.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.performanceLab.yourNote.model.Note;
import ru.performanceLab.yourNote.model.User;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    Note findNoteByUserAndName(User user, String noteName);

    List<Note> findNotesByUser(User user);
}
