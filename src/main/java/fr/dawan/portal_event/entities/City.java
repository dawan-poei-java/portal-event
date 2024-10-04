package fr.dawan.portal_event.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class City {

    public City(String name, String image){
        this.name = name;
        this.image = image;
    }

    public City(long id){
        this.id = id;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "city")
    private List<User> users;

    @Column(nullable = false)
    private String image;

}
