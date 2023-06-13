package com.procesos.concesionario.services;

import com.procesos.concesionario.models.User;
import com.procesos.concesionario.repository.UserRepository;
import com.procesos.concesionario.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.procesos.concesionario.utils.Constants.PASSWORD_INVALID;
import static com.procesos.concesionario.utils.Constants.USER_NOT_FOUND;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserById (Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> allUser() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User userDB = userRepository.findById(id).get();
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        //userDB.setPassword(user.getPassword());
        userDB.setAddress(user.getAddress());
        userDB.setBirthday(user.getBirthday());
        userDB.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userDB);
    }

    @Override
    public String login(User user) {
        Optional<User> userBd = userRepository.findByEmail(user.getEmail());
        //Optional<User> userBd = userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());//si es user,, en userRepository va User
        if (userBd.isEmpty()){
            throw new RuntimeException(USER_NOT_FOUND);
        }
        if(!passwordEncoder.matches(user.getPassword(), userBd.get().getPassword())){
            throw new RuntimeException(PASSWORD_INVALID);
        }
        return jwtUtil.create(String.valueOf(userBd.get().getId()),
                String.valueOf(userBd.get().getEmail()));
    }

}
