package com.example.Hotel.controllers;

import com.example.Hotel.models.Room;
import com.example.Hotel.repo.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class RoomOrderController {
    @Autowired
    private RoomsRepository  roomsRepository;



    @GetMapping("/room_order")
    public String room_order(Model model) {
        List<String> room_types=roomsRepository.groupByRoomType();
        model.addAttribute("room_types",room_types);
        model.addAttribute("title","Замовлення Номеру");
        return "room_order";
    }

    @GetMapping("/room_order/add")
    public String room_add(Model model) {


        List<String> room_types=roomsRepository.groupByRoomType();
        model.addAttribute("room_types",room_types);
        model.addAttribute("title","Замовлення Номеру");
        return "room_add";
    }

    @PostMapping("/room_order/add")
    public String room_addPost(@RequestParam int room_number,@RequestParam int room_service,@RequestParam String room_type, Model model) {
        Room room = new Room(room_number,room_service,room_type);

        while (!roomsRepository.findById(room.getId()).isEmpty()){
            room.setId(room.getId()+1);
        }
        roomsRepository.save(room);
        model.addAttribute("title","Додавання Номеру");
        return "redirect:/room_order";
    }
}
