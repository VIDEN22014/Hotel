package com.example.Hotel.controllers;

import com.example.Hotel.models.Room;
import com.example.Hotel.repo.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller


public class HotelController {
    @Autowired
    private RoomsRepository postRepository;


    @GetMapping("/hotel")
    public String hotelMain(Model model) {
Iterable<Room> posts = postRepository.findAll();
model.addAttribute("posts", posts);

        return "hotel-main";
    }
}
