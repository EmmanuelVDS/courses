package fr.manu.courses.services;

import fr.manu.courses.models.User;

import java.util.List;

public interface UserService {
    User findByUserEmail(String email);

    User addUser(User user);

    List<User> getUsers();
}
