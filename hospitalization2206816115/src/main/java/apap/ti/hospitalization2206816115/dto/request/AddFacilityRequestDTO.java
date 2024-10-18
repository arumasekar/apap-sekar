// AddFacilityRequestDTO.java
package apap.ti.hospitalization2206816115.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddFacilityRequestDTO {
    @NotNull
    private String name;
    
    @NotNull
    private Double fee;
}