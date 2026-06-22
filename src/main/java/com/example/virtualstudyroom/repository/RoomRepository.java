package com.example.virtualstudyroom.repository;

import com.example.virtualstudyroom.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Add custom queries if needed
    @Query("SELECT r FROM Room r WHERE r.name = ?1")
    Optional<Room> findRoomByName(String name);
}
