package com.example.virtualstudyroom.service;

import com.example.virtualstudyroom.dto.RoomRequest;
import com.example.virtualstudyroom.dto.RoomResponse;
import com.example.virtualstudyroom.model.Room;
import com.example.virtualstudyroom.model.User;
import com.example.virtualstudyroom.repository.RoomRepository;
import com.example.virtualstudyroom.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.datatransfer.ClipboardOwner;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository =  userRepository;
    }

    @Transactional
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public RoomResponse addNewRoom(RoomRequest request) {
        System.out.println("=== DEBUG ===");
        System.out.println("Received name: " + request);
        System.out.println("Received ownerId: " + request.ownerId());
        System.out.println("=============");
        // 1. Validate and fetch owner
        if (request.ownerId() == null) {
            throw new IllegalArgumentException("Owner ID is required");
        }

        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.ownerId()));

        // 2. Create Room entity
        Room room = new Room(request.name(), owner);

        // 3. Save once
        Room savedRoom = roomRepository.save(room);

        // 4. Return DTO (recommended)
        return new RoomResponse(
                savedRoom.getId(),
                savedRoom.getName(),
                owner.getId(),
                owner.getName()
        );
    }
}
