package fr.dawan.portal_event.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

@Entity
@Data
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "pricing")
    private List<Reservation> reservations;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
