package com.example.Hotel.repo;

import com.example.Hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomsRepository extends CrudRepository<Room, Long> {
    @Query(value = "SELECT room_type FROM `room` WHERE is_free=1 GROUP BY room_type",nativeQuery = true)
    List<String> groupByRoomType();
}
