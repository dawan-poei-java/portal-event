package fr.dawan.portal_event.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.event.DocumentEvent.EventType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    /*@Enumerated(EnumType.STRING)
    private EventType eventType;*/

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /*@ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;*/

    /*@ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;*/

    /*@ManyToMany
    @JoinTable(
        name = "event_participants",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )*/
    //private List<User> participants;

    private BigDecimal price;


}
