package apap.ti.hospitalization2206816115.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();;  // Using UUID as specified in the diagram

    @NotNull
    @Column(name = "NIK", length = 16, unique = true)  // NIK is a unique identifier
    private String nik;

    @NotNull
    @Column(name = "name", length = 100)  // Limit name to 100 characters
    private String name;

    @NotNull
    @Column(name = "email", unique = true, length = 100)  // Email should be unique
    private String email;

    @NotNull
    @Column(name = "gender")  // Assuming gender is stored as a boolean or L/P
    private boolean gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Column(name = "birth_date")  // Birth date of the patient
    private Date birthDate;

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

    // // One-to-many relationship with Reservation, with a SQL restriction for soft delete (is_deleted = false)
    // @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @SQLRestriction("is_deleted = false")  // Only fetch reservations where isDeleted = false
    // private List<Reservation> reservations;
}