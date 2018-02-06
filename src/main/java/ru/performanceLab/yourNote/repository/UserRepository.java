package ru.performanceLab.yourNote.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.performanceLab.yourNote.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
