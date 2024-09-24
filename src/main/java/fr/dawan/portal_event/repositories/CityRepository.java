package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.entities.City;

public interface CityRepository extends JpaRepository<City, Long>{

    City findByName(String name);
}
