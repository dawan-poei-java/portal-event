package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.entities.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
}
