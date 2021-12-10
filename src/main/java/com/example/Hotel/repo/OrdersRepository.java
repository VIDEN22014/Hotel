package com.example.Hotel.repo;

import com.example.Hotel.models.Orders;
import com.example.Hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT * FROM orders WHERE client_phone = ?1 AND room_number = ?2 LIMIT 1", nativeQuery = true)
    List<Orders> getFirstByClientPhone(String client_phone, String room_number);

    @Query(value = "SELECT COUNT(id) from orders where room_number = ?1 and ?2 BETWEEN check_in_date and check_out_date", nativeQuery = true)
    int getCountByRoomNumberAndDate(String room_number, LocalDate localDate);

    @Query(value = "SELECT COUNT(id) from orders where room_type = ?1 and ?2 BETWEEN check_in_date and check_out_date", nativeQuery = true)
    int getCountByRoomTypeAndDate(String room_type, LocalDate localDate);

    @Query(value = "SELECT MAX(check_out_date) from orders WHERE room_type = ?1", nativeQuery = true)
    LocalDate getMaxCheckOutDateByRoomType(String room_type);



}
