package ru.performanceLab.yourNote.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.performanceLab.yourNote.db.model.User;
import ru.performanceLab.yourNote.db.repository.UserRepository;

@Service
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User create(String name) {
        User user = new User();
        user.setName(name);
        return userRepository.save(user);
    }
}
