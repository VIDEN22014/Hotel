package com.example.Hotel.repo;

import com.example.Hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Room, Long> {

    @Query(value = "SELECT room_type FROM room GROUP BY room_type",nativeQuery = true)
    List<String> groupByRoomType();

    @Query(value = "SELECT room_number FROM room WHERE room_type = ?1",nativeQuery = true)
    List<String> getRoomNumbersByRoomType(String room_type);


    @Query(value = "SELECT * FROM room WHERE room_number = ?1",nativeQuery = true)
    Room getRoomByRoomNumber(int room_number);

    @Query(value = "SELECT count(id) FROM room WHERE room_number = ?1",nativeQuery = true)
    int getCountByRoomNumber(int room_number);
}
