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
            return "redirect:/admin";
        }
        model.addAttribute("error",1);
        return "admin/admin_entry";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("title","Сторінка Адміністратора");
        return "admin/admin";
    }

    @GetMapping("/admin/rooms")
    public String adminRooms(@RequestParam(value="id",required=false)Long id,Model model) {
        model.addAttribute("title","Зміна Кімнат");
        List<Room> rooms = roomsRepository.findAll();
        model.addAttribute("rooms",rooms);
        model.addAttribute("id",id);
        //Update Actions
        if (id!=null){
            Optional<Room> roomUpdate = roomsRepository.findById(id);
            model.addAttribute("room_type_update",roomUpdate.get().getRoom_type());
            model.addAttribute("room_number_update",roomUpdate.get().getRoom_number());
            model.addAttribute("is_free_update",roomUpdate.get().isFree());
            model.addAttribute("price_per_day_update",roomUpdate.get().getPrice_per_day());
        }
        return "admin/admin_rooms";
    }

    @PostMapping("/admin/rooms")
    public String adminRoomsPost(@RequestParam(value="id",required=false)Long id,@RequestParam String operation,
                                 @RequestParam String room_type,@RequestParam String room_number,@RequestParam(value="is_free",required=false)Boolean is_free,
                                 @RequestParam double price_per_day,Model model) {
        model.addAttribute("title","Зміна Кімнат");
        List<Room> rooms = roomsRepository.findAll();
        model.addAttribute("rooms",rooms);

        //Edit
        if (operation.equals("edit")){
            Room room = new Room(id,room_type,room_number,is_free,price_per_day);
            roomsRepository.save(room);
            Optional<Room> roomUpdate = roomsRepository.findById(id);
            model.addAttribute("id",id);
            model.addAttribute("room_type_update",roomUpdate.get().getRoom_type());
            model.addAttribute("room_number_update",roomUpdate.get().getRoom_number());
            model.addAttribute("is_free_update",roomUpdate.get().isFree());
            model.addAttribute("price_per_day_update",roomUpdate.get().getPrice_per_day());
            return "admin/admin_rooms";
        }else if(operation.equals("create")){
            Room room = new Room(room_type,room_number,price_per_day);
            roomsRepository.save(room);
        }
        return "redirect:/admin/rooms";
    }

    @PostMapping("/admin/rooms/delete")
    public String adminRoomsDelete(@RequestParam Long id, Model model) {
        model.addAttribute("title","Зміна Кімнат");
        roomsRepository.deleteById(id);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/admin/orders")
    public String adminOrders(Model model) {
        model.addAttribute("title","Зміна Замовлень");
        return "admin/admin_orders";
    }

}