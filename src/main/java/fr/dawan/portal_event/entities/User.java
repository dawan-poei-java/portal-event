package fr.dawan.portal_event.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import fr.dawan.portal_event.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;

    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String addressComplement;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(nullable = false)
    private String zipCode;


    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        if(this.userRole == null) this.userRole = UserRole.USER;
    }
}
