package apap.ti.hospitalization2206816115.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.hospitalization2206816115.model.Room;
import apap.ti.hospitalization2206816115.repository.RoomDb;
import apap.ti.hospitalization2206816115.dto.request.UpdateRoomRequestDTO;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomDb roomDb;
    
    private String generateRoomId() {
        // Mendapatkan jumlah room yang ada
        long roomCount = roomDb.count();
        
        // Format: RM + nomor urut dengan padding 3 digit
        // Contoh: RM001, RM002, dst
        String formattedNumber = String.format("%03d", roomCount + 1);
        return "RM" + formattedNumber;
    }

    @Override
    public void addRoom(Room room) {
        // Set ID room sebelum save
        room.setId(generateRoomId());
        room.setQuota(room.getMaxCapacity());
        roomDb.save(room);
    }

    @Override
    public List<Room> getAllRoom() {
        // Mengambil semua room yang tidak dihapus (soft delete)
        return roomDb.findAllByIsDeletedFalse();
    }

    @Override
    public Room getRoomById(String id) {
        return roomDb.findDetailRoomById(id)
                .orElse(null);
    }

    @Override
    public Room getRoomWithAvailability(String id, LocalDate dateIn, LocalDate dateOut) {
        if (dateIn == null || dateOut == null) {
            return getRoomById(id);
        }
        return roomDb.findRoomWithAvailability(id, dateIn, dateOut)
                .orElse(null);
    }

    @Override
    public Room updateRoom(String id, UpdateRoomRequestDTO updateRoomRequestDTO) {
        Room room = getRoomById(id);
        if (room != null) {
            room.setName(updateRoomRequestDTO.getName());
            room.setDescription(updateRoomRequestDTO.getDescription());
            room.setMaxCapacity(updateRoomRequestDTO.getMaxCapacity());
            room.setPricePerDay(updateRoomRequestDTO.getPricePerDay());
            return roomDb.save(room);
        }
        return null;
    }

    @Override
    public boolean deleteRoom(String id) {
        Room room = getRoomById(id);
        if (room != null) {
            room.setDeleted(true);
            roomDb.save(room);
            return true;
        }
        return false;
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate dateIn, LocalDate dateOut) {
        List<Room> allRooms = roomDb.findAllByIsDeletedFalse();
        return allRooms.stream()
                .filter(room -> isRoomAvailable(room, dateIn, dateOut))
                .collect(Collectors.toList());
    }

    private boolean isRoomAvailable(Room room, LocalDate dateIn, LocalDate dateOut) {
        Room availableRoom = getRoomWithAvailability(room.getId(), dateIn, dateOut);
        return availableRoom != null;
    }

    @Override
    public void updateQuota(String roomId, int quantity) {
        Room room = getRoomById(roomId);
        if (room != null) {
            int currentQuota = room.getQuota();
            room.setQuota(currentQuota + quantity);
            roomDb.save(room);
        }
    }

}