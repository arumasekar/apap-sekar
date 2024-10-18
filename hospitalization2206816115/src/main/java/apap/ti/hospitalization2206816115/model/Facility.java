package apap.ti.hospitalization2206816115.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facility")
public class Facility {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;  // UUID for facility identifier

    @NotNull
    @Column(name = "name", length = 100)  // Limit name to 100 characters
    private String name;

    @NotNull
    @Column(name = "fee")  // Fee associated with the facility
    private double fee;

    // @NotNull
    // @Column(name = "created_by", length = 50)  // Created by nurse's name
    // private String createdBy;

    // Automatically set the timestamp when a record is created
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    // @Column(name = "updated_by", length = 50)  // Updated by nurse's name
    // private String updatedBy;

    // Automatically update the timestamp when a record is updated
    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @NotNull
    @Column(name = "is_deleted")  // Soft delete field
    private boolean isDeleted;

    // Many-to-many relationship with Reservation
    @ManyToMany(mappedBy = "facilities", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @SQLRestriction("is_deleted = false")  // Only fetch reservations where isDeleted = false
    private List<Reservation> reservations;
}
