package com.example.virtualstudyroom.model;
import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        LocalDate dob,
        String email,
        int roomCount,
        int messageCount,
        int noteCount
) {
    // Constructor for basic info (without counts)
    public UserResponse(Long id, String name, LocalDate dob, String email) {
        this(id, name, dob, email, 0, 0, 0);
    }
}