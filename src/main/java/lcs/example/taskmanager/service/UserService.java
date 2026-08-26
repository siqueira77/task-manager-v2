package lcs.example.taskmanager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lcs.example.taskmanager.exceptions.DataConflict;
import lcs.example.taskmanager.model.User;
import lcs.example.taskmanager.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Usuário e senha são obrigatórios.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DataConflict("Este nome de usuário já está em uso.");
        }

        String cryptPassword = passwordEncoder.encode(password);
        User user = new User(username, cryptPassword);
        return userRepository.save(user);
    }
}
