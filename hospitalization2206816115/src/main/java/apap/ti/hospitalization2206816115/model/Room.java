package apap.ti.hospitalization2206816115.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "id", length = 50)  // Custom length for room ID
    private String id;

    @NotNull
    @Column(name = "name", length = 100)  // Limit name to 100 characters
    private String name;

    @Column(name = "description", length = 255)  // Optional description field
    private String description;

    @NotNull
    @Column(name = "max_capacity")  // Capacity of the room
    private int maxCapacity;

    @NotNull
    @Column(name = "price_per_day")  // Price per day for room
    private double pricePerDay;


    // Automatically set the timestamp when a record is created
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;


    // Automatically update the timestamp when a record is updated
    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @NotNull
    @Column(name = "is_deleted")  // Soft delete field
    private boolean isDeleted;

    // One-to-many relationship with Reservation, with a SQL restriction for soft delete (is_deleted = false)
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @SQLRestriction("is_deleted = false")  // Only fetch reservations where isDeleted = false
    private List<Reservation> reservations;

    @Column(name = "quota", nullable = false)
    private int quota;
    
    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }
}