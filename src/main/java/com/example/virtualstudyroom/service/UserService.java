package com.example.virtualstudyroom.service;

import com.example.virtualstudyroom.model.User;
import com.example.virtualstudyroom.model.UserResponse;
import com.example.virtualstudyroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse addNewUser(User user) {
        // Important: If user has an ID, treat it as update, else as new
        if (user.getId() != null) {
            // This is an update
            User existing = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            existing.setName(user.getName());
            existing.setDob(user.getDob());
            existing.setEmail(user.getEmail());

            User saved = userRepository.save(existing);
            return convertToResponse(saved);
        }

        // This is a new user
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("email taken");
        }

        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getDob(),
                user.getEmail(),
                user.getRooms() != null ? user.getRooms().size() : 0,
                user.getMessages() != null ? user.getMessages().size() : 0,
                user.getNotes() != null ? user.getNotes().size() : 0
        );
    }
}
