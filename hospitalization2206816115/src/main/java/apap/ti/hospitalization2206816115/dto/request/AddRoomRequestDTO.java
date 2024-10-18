package apap.ti.hospitalization2206816115.dto.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddRoomRequestDTO {
    private String nama;
    private String description;
    private int maxCapacity;
    private double pricePerDay;
    private int quotaAvailable;
}