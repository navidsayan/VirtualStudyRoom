package com.example.virtualstudyroom.controller;

import com.example.virtualstudyroom.dto.RoomRequest;
import com.example.virtualstudyroom.dto.RoomResponse;
import com.example.virtualstudyroom.model.Room;
import com.example.virtualstudyroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request) {
        System.out.println("Controller received: " + request);   // debug
        RoomResponse response = roomService.addNewRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
