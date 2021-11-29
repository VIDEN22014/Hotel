package com.example.Hotel.controllers;

import com.example.Hotel.models.Orders;
import com.example.Hotel.models.Room;
import com.example.Hotel.repo.OrdersRepository;
import com.example.Hotel.repo.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class RoomController {
    @Autowired
    private RoomsRepository  roomsRepository;

    @Autowired
    private OrdersRepository ordersRepository;



    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("title","Список Номерів");
        List<String> room_types=roomsRepository.groupByRoomType();
        model.addAttribute("room_types",room_types);
        return "/rooms/rooms";
    }

    @GetMapping("/rooms/room_order")
    public String room_order(@RequestParam String room_type,Model model) {
        model.addAttribute("title","Замовлення Номеру");
        model.addAttribute("room_type",room_type);
        return "rooms/room_order";
    }

    @PostMapping("/rooms/room_order")
    public String room_order(@RequestParam String room_type,@RequestParam String name,@RequestParam String surname,@RequestParam String phone_number,Model model) {
        model.addAttribute("title","Замовлення Номеру");
        model.addAttribute("room_type",room_type);
        List<Room> availableRoom=roomsRepository.getFirstByRoomType(room_type);
        if (availableRoom.isEmpty()){
            return "rooms/room_order";
        }else {
            Orders order=new Orders(name,surname,phone_number,availableRoom.get(0).getRoom_number(),
                    0,availableRoom.get(0).getPrice_per_day());
            ordersRepository.save(order);

            Optional<Room> room= roomsRepository.findById(availableRoom.get(0).getId());
            room.get().setFree(false);
            roomsRepository.save(room.get());

        }


        return "rooms/room_order";
    }

    @GetMapping("/rooms/room_order_success")
    public String room_order_success(@RequestParam String room_type,@RequestParam int room_number,Model model) {
        model.addAttribute("title","Успішне Оформлення Номеру");
        model.addAttribute("room_type",room_type);
        model.addAttribute("room_number",room_number);
        return "rooms/room_order";
    }

}
