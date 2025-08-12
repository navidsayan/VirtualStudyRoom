package com.example.virtualstudyroom.controller;

import com.example.virtualstudyroom.model.Room;
import com.example.virtualstudyroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // GET all rooms
    @GetMapping
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    // POST new room
    @PostMapping
    public void createRoom(@RequestBody Room room) {
        roomService.addNewRoom(room);
    }
}
