package apap.ti.hospitalization2206816115.service;

import apap.ti.hospitalization2206816115.model.Room;
import apap.ti.hospitalization2206816115.dto.request.UpdateRoomRequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    void addRoom(Room room);
    List<Room> getAllRoom();
    Room getRoomById(String id);
    Room getRoomWithAvailability(String id, LocalDate dateIn, LocalDate dateOut);
    Room updateRoom(String id, UpdateRoomRequestDTO updateRoomRequestDTO);
    boolean deleteRoom(String id);
    // Metode baru untuk mendapatkan semua kamar yang tersedia
    List<Room> getAvailableRooms(LocalDate dateIn, LocalDate dateOut);
    void updateQuota(String roomId, int quantity);
}