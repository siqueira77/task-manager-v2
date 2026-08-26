package lcs.example.taskmanager.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.User;
import lcs.example.taskmanager.repository.UserRepository;

@Component
public class CurrentUserProvider {

    private final UserRepository userRepository;

    public CurrentUserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFound("Usuário autenticado não encontrado."));
    }
}
