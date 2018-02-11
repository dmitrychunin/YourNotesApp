package ru.performanceLab.yourNote.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.performanceLab.yourNote.db.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
