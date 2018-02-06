package ru.performanceLab.yourNote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.performanceLab.yourNote.model.Note;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    @Query(value = "SELECT * FROM NOTE WHERE USER_ID = ?1 AND NAME = ?2", nativeQuery = true)
    Note findByUserIdAndNoteName(Long userId, String noteName);

    @Query(value = "SELECT * FROM NOTE WHERE USER_ID = ?1", nativeQuery = true)
    List<Note> findNotesByUserId(Long id);
}
