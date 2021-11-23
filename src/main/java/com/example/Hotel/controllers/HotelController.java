package com.example.Hotel.controllers;

import com.example.Hotel.models.Post;
import com.example.Hotel.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller


public class HotelController {
    @Autowired
    private PostRepository postRepository;


    @GetMapping("/hotel")
    public String hotelMain(Model model) {
Iterable<Post> posts = postRepository.findAll();
model.addAttribute("posts", posts);

        return "hotel-main";
    }
}
