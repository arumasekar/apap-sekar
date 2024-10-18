package apap.ti.hospitalization2206816115.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import apap.ti.hospitalization2206816115.dto.request.AddRoomRequestDTO;
import apap.ti.hospitalization2206816115.dto.request.UpdateRoomRequestDTO;
import apap.ti.hospitalization2206816115.model.Room;
import apap.ti.hospitalization2206816115.service.RoomService;
import apap.ti.hospitalization2206816115.repository.RoomDb;

@Controller
public class RoomController {
    
    @Autowired
    RoomService roomService;
    private RoomDb roomDb;
    
    @GetMapping("/room/create")
    public String formAddRoom(Model model) {
        var roomDTO = new AddRoomRequestDTO();
        model.addAttribute("roomDTO", roomDTO);
        return "form-add-room";
    }
    
    @PostMapping("/room/create")
    public String addRoom(@ModelAttribute("roomDTO") AddRoomRequestDTO roomDTO, Model model) {
        var room = new Room();
        
        // Set room properties
        room.setName(roomDTO.getNama());
        room.setDescription(roomDTO.getDescription());
        room.setMaxCapacity(roomDTO.getMaxCapacity());
        room.setPricePerDay(roomDTO.getPricePerDay());
        room.setDeleted(false);
        
        // ID akan di-generate di service
        roomService.addRoom(room);
        
        model.addAttribute("responseMessage",
            String.format("Ruangan %s dengan ID %s berhasil ditambahkan.", 
                room.getName(), room.getId()));
        return "response-room";
    }

    @GetMapping("/room")
    public String listRoom(Model model) {
        List<Room> listRoom = roomService.getAllRoom();

        model.addAttribute("listRoom", listRoom);
        return "viewall-room";
    }

    @GetMapping("/room/{id}")
    public String viewRoom(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateIn,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOut,
            Model model
    ) {
        Room room;
        if (dateIn != null && dateOut != null) {
            room = roomService.getRoomWithAvailability(id, dateIn, dateOut);
        } else {
            room = roomService.getRoomById(id);
        }

        if (room == null) {
            return "error/404";
        }

        model.addAttribute("room", room);
        model.addAttribute("dateIn", dateIn);
        model.addAttribute("dateOut", dateOut);
        return "view-room";
    }

    @GetMapping("/room/{id}/update")
    public String formUpdateRoom(@PathVariable String id, Model model) {
        Room room = roomService.getRoomById(id);
        if (room == null) {
            return "error/404";
        }
        UpdateRoomRequestDTO updateRoomRequestDTO = new UpdateRoomRequestDTO();
        updateRoomRequestDTO.setName(room.getName());
        updateRoomRequestDTO.setDescription(room.getDescription());
        updateRoomRequestDTO.setMaxCapacity(room.getMaxCapacity());
        updateRoomRequestDTO.setPricePerDay(room.getPricePerDay());
        model.addAttribute("room", room);
        model.addAttribute("updateRoomRequestDTO", updateRoomRequestDTO);
        return "form-update-room";
    }

    @PostMapping("/room/{id}/update")
    public String updateRoom(@PathVariable String id, @ModelAttribute UpdateRoomRequestDTO updateRoomRequestDTO, Model model) {
        Room updatedRoom = roomService.updateRoom(id, updateRoomRequestDTO);
        if (updatedRoom == null) {
            return "error/404";
        }
        model.addAttribute("responseMessage", String.format("Ruangan %s dengan ID %s berhasil diperbarui.", updatedRoom.getName(), updatedRoom.getId()));
        return "response-room";
    }

    @PostMapping("/room/{id}/delete")
    public String deleteRoom(@PathVariable String id, Model model) {
        Room room = roomService.getRoomById(id);
        if (room == null) {
            return "error/404";
        }
        
        boolean isDeleted = roomService.deleteRoom(id);
        if (isDeleted) {
            model.addAttribute("responseMessage", String.format("Ruangan %s dengan ID %s berhasil dihapus.", room.getName(), room.getId()));
        } else {
            model.addAttribute("responseMessage", "Gagal menghapus ruangan. Silakan coba lagi.");
        }
        return "response-room";
    }

    
}