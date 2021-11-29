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
public class AdminController {
    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private OrdersRepository ordersRepository;



    @GetMapping("/admin_entry")
    public String admin_entry(Model model) {
        model.addAttribute("title","Сторінка Адміністратора");
        return "admin/admin_entry";
    }

    @PostMapping("/admin_entry")
    public String admin_entryPost(@RequestParam String password, Model model) {
        model.addAttribute("title","Сторінка Адміністратора");
        if (password.equals("admin")){
            return "admin/admin";
        }
        model.addAttribute("error",1);
        return "admin/admin_entry";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("title","Сторінка Адміністратора");
        return "admin/admin";
    }

    @PostMapping("/admin")
    public String adminPost(@RequestParam String password, Model model) {
        model.addAttribute("title","Сторінка Адміністратора");
        if (password.equals("admin")){
            return "admin/admin";
        }
        model.addAttribute("error",1);
        return "admin/admin_entry";
    }

}