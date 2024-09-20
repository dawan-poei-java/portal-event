package fr.dawan.portal_event.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class TypeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private  String name;
    @OneToMany(mappedBy = "typeEvent")
    private List<Event> events;
}
