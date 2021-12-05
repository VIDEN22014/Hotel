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
    public String room_order(@RequestParam String room_type,@RequestParam String name,@RequestParam String surname,
                             @RequestParam String phone_number,@RequestParam String service,Model model) {
        model.addAttribute("title","Замовлення Номеру");
        model.addAttribute("room_type",room_type);
        List<Room> availableRoom=roomsRepository.getFirstByRoomType(room_type);
        if (availableRoom.isEmpty()){
            return "redirect:/rooms";
        }else {
            Orders order=new Orders(name,surname,phone_number,service,availableRoom.get(0).getRoom_number(),room_type
                    ,availableRoom.get(0).getPrice_per_day());
            ordersRepository.save(order);

            Optional<Room> room= roomsRepository.findById(availableRoom.get(0).getId());
            room.get().setFree(false);
            roomsRepository.save(room.get());
            model.addAttribute("title","Успішне Оформлення Номеру");
            model.addAttribute("room_number",room.get().getRoom_number());
            return "rooms/room_order_success";
        }
    }

    @GetMapping("/rooms/room_order_success")
    public String room_order_success(@RequestParam String room_type,@RequestParam String room_number,Model model) {
        model.addAttribute("title","Успішне Оформлення Номеру");
        model.addAttribute("room_type",room_type);
        model.addAttribute("room_number",room_number);
        return "rooms/room_order_success";
    }
    @GetMapping("/room_service")
    public String room_service(Model model) {
        model.addAttribute("title","Зміна сервісу в номері");
        model.addAttribute("state","none");
        return "/rooms/room_service";
    }

    @PostMapping("/room_service")
    public String room_servicePost(@RequestParam String room_number,@RequestParam String phone_number,@RequestParam String service,Model model) {
        model.addAttribute("title","Зміна сервісу в номері");
        List<Orders> orderToChangeList=ordersRepository.getFirstByClientPhone(phone_number,room_number);
        if (orderToChangeList.isEmpty()){
            model.addAttribute("state","error");
            return "/rooms/room_service";
        }else {
            Optional<Orders> order= ordersRepository.findById(orderToChangeList.get(0).getId());
            order.get().setRoom_service(service);
            ordersRepository.save(order.get());
            model.addAttribute("state","success");
            return "/rooms/room_service";
        }
    }


}
