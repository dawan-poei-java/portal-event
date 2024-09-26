package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
