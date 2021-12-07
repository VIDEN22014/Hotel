package com.example.Hotel.controllers;

import com.example.Hotel.models.Orders;
import com.example.Hotel.models.Room;
import com.example.Hotel.repo.OrdersRepository;
import com.example.Hotel.repo.RoomsRepository;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
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
        model.addAttribute("title", "Сторінка Адміністратора");
        return "admin/admin_entry";
    }

    @PostMapping("/admin_entry")
    public String admin_entryPost(@RequestParam String password, Model model) {
        model.addAttribute("title", "Сторінка Адміністратора");
        if (password.equals("admin")) {
            return "redirect:/admin";
        }
        model.addAttribute("error", 1);
        return "admin/admin_entry";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("title", "Сторінка Адміністратора");
        return "admin/admin";
    }

    @GetMapping("/admin/rooms")
    public String adminRooms(@RequestParam(value = "id", required = false) Long id, Model model) {
        model.addAttribute("title", "Зміна Кімнат");
        List<Room> rooms = roomsRepository.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("id", id);
        //Update Actions
        if (id != null) {
            Optional<Room> roomUpdate = roomsRepository.findById(id);
            model.addAttribute("room_update", roomUpdate.get());
        }
        return "admin/admin_rooms";
    }

    @PostMapping("/admin/rooms")
    public String adminRoomsPost(@RequestParam(value = "id", required = false) Long id, @RequestParam String operation,
                                 @RequestParam String room_type, @RequestParam int room_number, @RequestParam(value = "is_free", required = false) Boolean is_free,
                                 @RequestParam int price_per_day, Model model) {
        model.addAttribute("title", "Зміна Кімнат");
        List<Room> rooms = roomsRepository.findAll();
        model.addAttribute("rooms", rooms);

        //Edit
        if (operation.equals("edit")) {
            Room room = new Room(id, room_type, room_number, is_free, price_per_day);
            Optional<Room> roomUpdate = roomsRepository.findById(id);
            model.addAttribute("id", id);
            model.addAttribute("room_update", roomUpdate.get());
            if (roomsRepository.getCountByRoomNumber(room_number) > 0) {
                model.addAttribute("isRoomNumberExists", true);
            } else {
                roomsRepository.save(room);
                model.addAttribute("isRoomNumberExists", false);
            }
         } else if (operation.equals("create")) {
            Room room = new Room(room_type, room_number, price_per_day);
            if (roomsRepository.getCountByRoomNumber(room_number) > 0) {
                model.addAttribute("isRoomNumberExists", true);
                model.addAttribute("room_create_room_type", room_type);
                model.addAttribute("room_create_room_number", room_number);
                model.addAttribute("room_create_price_per_day", price_per_day);
            } else {
                roomsRepository.save(room);
                model.addAttribute("isRoomNumberExists", false);
            }
        }
        return "admin/admin_rooms";
    }

    @PostMapping("/admin/rooms/delete")
    public String adminRoomsDelete(@RequestParam Long id, Model model) {
        model.addAttribute("title", "Зміна Кімнат");
        roomsRepository.deleteById(id);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/admin/orders")
    public String adminOrders(@RequestParam(value = "id", required = false) Long id, Model model) {
        model.addAttribute("title", "Зміна Замовлень");
        List<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("id", id);
        //Update Actions
        if (id != null) {
            Optional<Orders> ordersUpdate = ordersRepository.findById(id);
            model.addAttribute("orderUpdate", ordersUpdate.get());
        }
        return "admin/admin_orders";
    }

    @PostMapping("/admin/orders")
    public String adminOrdersPost(@RequestParam(value = "id", required = false) Long id, @RequestParam String operation,
                                  @RequestParam String name, @RequestParam String surname,
                                  @RequestParam String phone_number, @RequestParam int room_number,
                                  @RequestParam String room_type, @RequestParam String service, @RequestParam int order_amount,
                                  Model model) {
        model.addAttribute("title", "Зміна Замовлень");
        List<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);
        //Edit
        if (operation.equals("edit")) {
            Orders order = new Orders(name, surname, phone_number, service, room_number, room_type, order_amount);
            order.setId(id);
            ordersRepository.save(order);
            model.addAttribute("id", id);
            model.addAttribute("orderUpdate", order);
            return "admin/admin_orders";
        }
        return "redirect:/admin/orders";
    }

    @PostMapping("/admin/orders/delete")
    public String adminOrdersDelete(@RequestParam Long id, Model model) {
        model.addAttribute("title", "Зміна Кімнат");
        ordersRepository.deleteById(id);
        return "redirect:/admin/orders";
    }


}