package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.entities.TypeEvent;

public interface TypeEventRepository extends JpaRepository<TypeEvent, Long> {

}
