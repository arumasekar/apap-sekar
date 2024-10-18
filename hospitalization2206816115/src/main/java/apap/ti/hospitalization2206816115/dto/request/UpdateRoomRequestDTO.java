package apap.ti.hospitalization2206816115.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoomRequestDTO {
    private String name;
    private String description;
    private int maxCapacity;
    private double pricePerDay;
}