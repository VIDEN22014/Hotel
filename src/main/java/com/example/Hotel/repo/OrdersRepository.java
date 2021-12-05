package com.example.Hotel.repo;

import com.example.Hotel.models.Orders;
import com.example.Hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT * FROM orders WHERE client_phone = ?1 AND room_number = ?2 LIMIT 1",nativeQuery = true)
    List<Orders> getFirstByClientPhone(String client_phone,String room_number);

}
