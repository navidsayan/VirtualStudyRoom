package com.example.virtualstudyroom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomRequest(
        @JsonProperty("name")
        String name,
        @JsonProperty("ownerId")
        Long ownerId
) {
    // Optional validation
    public RoomRequest {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name is required");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID is required");
        }
    }
}