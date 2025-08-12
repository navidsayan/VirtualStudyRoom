package com.example.virtualstudyroom.service;

import com.example.virtualstudyroom.model.Room;
import com.example.virtualstudyroom.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public void addNewRoom(Room room) {
        // Optional: Add validation logic here if needed
        roomRepository.save(room);
    }
}
