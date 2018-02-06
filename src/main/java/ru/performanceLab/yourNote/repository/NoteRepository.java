package ru.performanceLab.yourNote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.performanceLab.yourNote.model.Note;
import ru.performanceLab.yourNote.model.User;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    @Query(value = "SELECT * FROM NOTE WHERE USER_ID = ?1 AND NAME = ?2", nativeQuery = true)
    Note findByUserIdAndNoteName(Long userId, String noteName);

    @Query(value = "SELECT * FROM NOTE WHERE USER_ID = ?1", nativeQuery = true)
    List<Note> findNotesByUserId(Long id);
}

//    private static final SessionFactory sessionFactory;
//
//    static {
//        try {
//            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//        } catch (Throwable ex) {
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    public void deleteNoteByName(String noteName) {
//        try (Session session = sessionFactory.openSession()) {
//            Query query = session.createQuery("delete Note where noteName = :noteName");
//            query.setParameter("noteName", noteName);
//            int result = query.executeUpdate();
//        }
//    }
//
//    public void updateNoteByName(String noteName, String noteText) {
//        try (Session session = sessionFactory.openSession()) {
//            Query query = session.createQuery("update Note set noteText = :noteText where noteName = :noteName");
//            query.setParameter("noteName", noteName);
//            query.setParameter("noteText", noteText);
//            int result = query.executeUpdate();
//        }
//    }
//
//    @Override
//    public <S extends Note> S save(S s) {
//        return null;
//    }
//
//    @Override
//    public <S extends Note> Iterable<S> save(Iterable<S> iterable) {
//        return null;
//    }
//
//    @Override
//    public Note findOne(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public boolean exists(Long aLong) {
//        return false;
//    }
//
//    @Override
//    public Iterable<Note> findAll() {
//        return null;
//    }
//
//    @Override
//    public Iterable<Note> findAll(Iterable<Long> iterable) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void delete(Long aLong) {
//
//    }
//
//    @Override
//    public void delete(Note note) {
//
//    }
//
//    @Override
//    public void delete(Iterable<? extends Note> iterable) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
