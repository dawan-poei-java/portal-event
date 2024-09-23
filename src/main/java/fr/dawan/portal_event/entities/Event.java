package fr.dawan.portal_event.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.swing.event.DocumentEvent.EventType;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Event {

    public Event(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "event_type")
    private TypeEvent typeEvent;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private String location;
    @Column(nullable = false)
    private String address;
    private String addressComplement;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    private String zipCode;


    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Pricing> pricings;

    /*@ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;*/

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @ManyToMany
    @JoinTable(
        name = "event_participants",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;

}
