package apap.ti.hospitalization2206816115.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;  // Generated identifier for the reservation

    @NotNull
    @Column(name = "date_in", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateIn;  // Date of check-in

    @NotNull
    @Column(name = "date_out", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOut;  // Date of check-out

    @NotNull
    @Column(name = "total_fee", nullable = false)
    private double totalFee;  // Total fee for the reservation

    // Automatically set the timestamp when a record is created
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    // Automatically update the timestamp when a record is updated
    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;


    @NotNull
    @Column(name = "is_deleted", nullable = false)  // Soft delete field
    private boolean isDeleted;

    // Many-to-one relationship with Patient (each reservation is made by one patient)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Many-to-one relationship with Room (each reservation is for one room)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // Many-to-one relationship with Nurse (each reservation is handled by one nurse)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false)  // New relationship with Nurse
    private Nurse nurse;

    // Many-to-many relationship with Facility (each reservation can include multiple facilities)
    @ManyToMany
    @JoinTable(
        name = "reservation_facility",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private List<Facility> facilities;

    public void setDateIn(LocalDate reservationDateIn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDateIn'");
    }

    public void setDateOut(LocalDate reservationDateOut) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDateOut'");
    }
}
