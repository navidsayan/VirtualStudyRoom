package com.example.virtualstudyroom.repository;

import com.example.virtualstudyroom.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Add custom queries if needed
}
