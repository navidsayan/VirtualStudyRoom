package com.example.virtualstudyroom.dto;

public class RoomResponse {

    private Long id;
    private String name;
    private Long ownerId;
    private String ownerName;

    public RoomResponse() {}

    public RoomResponse(Long id, String name, Long ownerId, String ownerName) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    // Getters only (DTOs should be immutable)
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getOwnerId() { return ownerId; }
    public String getOwnerName() { return ownerName; }
}