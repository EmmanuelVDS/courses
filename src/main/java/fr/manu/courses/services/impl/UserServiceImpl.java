package fr.manu.courses.services.impl;

import fr.manu.courses.dao.UserDao;
import fr.manu.courses.models.User;
import fr.manu.courses.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByUserEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User addUser(User user) {

        return userDao.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }
}
