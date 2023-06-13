package com.procesos.concesionario.services;

import com.procesos.concesionario.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(Long id);
    List<User> allUser();
    User createUser(User user);
    User updateUser(Long id,User user);
    String login(User user);
}
