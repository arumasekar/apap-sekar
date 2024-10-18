package apap.ti.hospitalization2206816115;

import apap.ti.hospitalization2206816115.service.FacilityService;
import apap.ti.hospitalization2206816115.service.NurseService;
import apap.ti.hospitalization2206816115.service.RoomService;
import apap.ti.hospitalization2206816115.dto.request.AddFacilityRequestDTO;
import apap.ti.hospitalization2206816115.dto.request.AddNurseRequestDTO;
import apap.ti.hospitalization2206816115.dto.request.AddRoomRequestDTO;
import apap.ti.hospitalization2206816115.model.Room;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.UUID;

@SpringBootApplication
public class Hospitalization2206816115Application {

    public static void main(String[] args) {
        SpringApplication.run(Hospitalization2206816115Application.class, args);
    }

    @Bean
@Transactional
CommandLineRunner run(FacilityService facilityService, NurseService nurseService, RoomService roomService) {
    return args -> {
        var faker = new Faker(new Locale("in-ID"));

        // Generate fake facilities
        for (int i = 0; i < 5; i++) {
            var facilityDTO = new AddFacilityRequestDTO();
            facilityDTO.setName(faker.medical().hospitalName() + " Facility");
            facilityDTO.setFee(faker.number().randomDouble(2, 100000, 1000000));

            facilityService.createFacility(facilityDTO);
        }

        // Generate fake nurses
        for (int i = 0; i < 5; i++) {
            var nurseDTO = new AddNurseRequestDTO();
            nurseDTO.setName(faker.name().fullName());
            nurseDTO.setEmail(faker.internet().emailAddress());
            nurseDTO.setGender(faker.bool().bool() ? "L" : "P");

            nurseService.createNurse(nurseDTO);
        }

        // Generate fake rooms
        for (int i = 0; i < 5; i++) {
            var roomDTO = new AddRoomRequestDTO();
            roomDTO.setNama(faker.commerce().productName() + " Room");
            roomDTO.setDescription(faker.lorem().sentence());
            roomDTO.setMaxCapacity(faker.number().numberBetween(1, 5));
            roomDTO.setPricePerDay(faker.number().randomDouble(2, 100000, 1000000));

            var room = new Room();
            room.setId("RM" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
            room.setName(roomDTO.getNama());
            room.setDescription(roomDTO.getDescription());
            room.setMaxCapacity(roomDTO.getMaxCapacity());
            room.setPricePerDay(roomDTO.getPricePerDay());
            room.setDeleted(false);

            roomService.addRoom(room);
        }
    };
}

}