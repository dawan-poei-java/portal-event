package fr.dawan.portal_event.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "city")
    private List<User> users;

}
