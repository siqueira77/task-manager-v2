package lcs.example.taskmanager.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lcs.example.taskmanager.model.User;
import lcs.example.taskmanager.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(String username, String password){
        String cryptPassword = passwordEncoder.encode(password);
        User user = new User(username, cryptPassword); 
        return userRepository.save(user);
    }

    public Optional<User> findUser(String username){
        return userRepository.findByUsername(username);
    }
}
