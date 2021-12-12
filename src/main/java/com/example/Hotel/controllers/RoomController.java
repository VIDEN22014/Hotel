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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class RoomController {
    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private OrdersRepository ordersRepository;


    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("title", "Список Номерів");
        List<String> room_types = roomsRepository.groupByRoomType();
        model.addAttribute("room_types", room_types);
        return "/rooms/rooms";
    }

    @GetMapping("/rooms/room_order")
    public String room_order(@RequestParam String room_type, Model model) {
        model.addAttribute("title", "Замовлення Номеру");
        model.addAttribute("room_type", room_type);
        //DatesDisabled Check
        List<String> datesDisabledList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate maxCheckOut = ordersRepository.getMaxCheckOutDateByRoomType(room_type);
        if (maxCheckOut!=null){
            for (LocalDate i = today; i.compareTo(maxCheckOut) <= 0; i = i.plusDays(1)) {
                if (roomsRepository.getCountByRoomType(room_type)==ordersRepository.getCountByRoomTypeAndDate(room_type,i)){
                    datesDisabledList.add(i.toString());
                    System.out.println(i.toString());
                }
            }
        }
        String[] datesDisabledArray = datesDisabledList.toArray(new String[0]);
        model.addAttribute("datesDisabled", datesDisabledArray);

        return "rooms/room_order";
    }

    @PostMapping("/rooms/room_order")
    public String room_order(@RequestParam String room_type, @RequestParam String name, @RequestParam String surname,
                             @RequestParam String phone_number, @RequestParam String service, @RequestParam int number_of_people,
                             @RequestParam String datepicker1, @RequestParam String datepicker2, Model model) {
        model.addAttribute("title", "Замовлення Номеру");
        model.addAttribute("room_type", room_type);
        //Date Check
        String isRoomAvailable = "NotAvailable";
        LocalDate checkIn = LocalDate.parse(datepicker1);
        LocalDate checkOut = LocalDate.parse(datepicker2);
        List<String> roomNumbers = roomsRepository.getRoomNumbersByRoomType(room_type);
        for (int i = 0; i < roomNumbers.size(); i++) {
            for (LocalDate j = checkIn; j.compareTo(checkOut) <= 0; j = j.plusDays(1)) {
                if (ordersRepository.getCountByRoomNumberAndDate(roomNumbers.get(i), j) > 0) {
                    roomNumbers.set(i, "NotAvailable");
                }
            }
        }
        for (int i = 0; i < roomNumbers.size(); i++) {
            if (!roomNumbers.get(i).equals("NotAvailable")) {
                isRoomAvailable = roomNumbers.get(i);
                break;
            }
        }
        System.out.println(isRoomAvailable);


        if (isRoomAvailable.equals("NotAvailable")||checkIn.compareTo(checkOut)>0) {
            //DatesDisabled Check
            List<String> datesDisabledList = new ArrayList<>();
            LocalDate today = LocalDate.now();
            LocalDate maxCheckOut = ordersRepository.getMaxCheckOutDateByRoomType(room_type);
            for (LocalDate i = today; i.compareTo(maxCheckOut) <= 0; i = i.plusDays(1)) {
                if (roomsRepository.getCountByRoomType(room_type)==ordersRepository.getCountByRoomTypeAndDate(room_type,i)){
                    datesDisabledList.add(i.toString());
                    System.out.println(i.toString());
                }
            }
            String[] datesDisabledArray = datesDisabledList.toArray(new String[0]);
            model.addAttribute("datesDisabled", datesDisabledArray);
            model.addAttribute("room_type", room_type);
            model.addAttribute("name", name);
            model.addAttribute("surname", surname);
            model.addAttribute("phone_number", phone_number);
            model.addAttribute("number_of_people", number_of_people);
            model.addAttribute("service", service);
            model.addAttribute("datepicker1", datepicker1);
            model.addAttribute("datepicker2", datepicker2);
            if (isRoomAvailable.equals("NotAvailable")){
                model.addAttribute("isRoomAvailable", false);
            }
            else if (checkIn.compareTo(checkOut)>0){
                model.addAttribute("isDatepickerWrong", true);
            }

            return "rooms/room_order";
        } else {
            Room availableRoom = roomsRepository.getRoomByRoomNumber(Integer.parseInt(isRoomAvailable));
            int amount = 0;
            for (LocalDate i = checkIn; i.compareTo(checkOut) <= 0; i = i.plusDays(1)) {
                amount += availableRoom.getPrice_per_day();
            }
            amount *= number_of_people;

            Orders order = new Orders(name, surname, phone_number, service, availableRoom.getRoom_number(), room_type,
                    number_of_people, amount, LocalDate.parse(datepicker1), LocalDate.parse(datepicker2));
            ordersRepository.save(order);

            model.addAttribute("title", "Успішне Оформлення Номеру");
            model.addAttribute("room_number", availableRoom.getRoom_number());
            return "rooms/room_order_success";
        }
    }

    @GetMapping("/rooms/room_order_success")
    public String room_order_success(@RequestParam String room_type, @RequestParam String room_number, Model model) {
        model.addAttribute("title", "Успішне Оформлення Номеру");
        model.addAttribute("room_type", room_type);
        model.addAttribute("room_number", room_number);
        return "rooms/room_order_success";
    }

    @GetMapping("/room_service")
    public String room_service(Model model) {
        model.addAttribute("title", "Зміна сервісу в номері");
        model.addAttribute("state", "none");
        return "/rooms/room_service";
    }

    @PostMapping("/room_service")
    public String room_servicePost(@RequestParam String room_number, @RequestParam String phone_number, @RequestParam String service, Model model) {
        model.addAttribute("title", "Зміна сервісу в номері");
        List<Orders> orderToChangeList = ordersRepository.getFirstByClientPhone(phone_number, room_number);
        if (orderToChangeList.isEmpty()) {
            model.addAttribute("state", "error");
            return "/rooms/room_service";
        } else {
            Optional<Orders> order = ordersRepository.findById(orderToChangeList.get(0).getId());
            order.get().setRoom_service(service);
            ordersRepository.save(order.get());
            model.addAttribute("state", "success");
            return "/rooms/room_service";
        }
    }


}
