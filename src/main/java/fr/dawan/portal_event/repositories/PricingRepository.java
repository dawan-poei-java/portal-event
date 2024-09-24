package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.entities.Pricing;
public interface PricingRepository extends JpaRepository<Pricing, Long> {

}
