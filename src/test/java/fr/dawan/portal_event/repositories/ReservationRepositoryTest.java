package fr.dawan.portal_event.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.entities.Pricing;
import fr.dawan.portal_event.entities.Reservation;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.utils.TestUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PricingRepository pricingRepository;

    @Autowired
    private CityRepository cityRepository;

    @Test
    void testFindAll() {
        // Créer quelques réservations de test
        Reservation reservation1 = new Reservation();

        // Créer une ville
        City paris = new City();
        paris.setName("Paris");
        paris.setImage("url_image_paris");
        paris = cityRepository.save(paris);

        User user = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.ORGANIZER, paris, null, userRepository);
        
        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), user.getCity(), user);
        event = eventRepository.save(event);
        
        // Créer un Pricing
        Pricing pricing = new Pricing();
        pricing.setName("Tarif standard");
        pricing.setPrice(50.0);
        pricing.setEvent(event);
        pricing = pricingRepository.save(pricing);
        
        event.setPricings(new ArrayList<>());
        // Associer le pricing à l'événement
        event.getPricings().add(pricing);
        eventRepository.save(event);
        
        reservation1.setPricing(pricing);
        reservation1.setUser(user);

        Reservation reservation2 = new Reservation();
        reservationRepository.save(reservation1);
        User user2 = TestUtils.createFullUser("Marie", "Dubois", "marie.dubois@example.com", UserRole.USER, paris, "0987654321", userRepository);
        reservation2.setUser(user2);
        reservation2.setPricing(pricing);
        reservationRepository.save(reservation2);

        // Récupérer toutes les réservations
        List<Reservation> reservations = reservationRepository.findAll();

        // Vérifier que la liste n'est pas vide et contient le bon nombre de réservations
        assertFalse(reservations.isEmpty());
        assertEquals(2, reservations.size());

        // Vérifier que les réservations récupérées correspondent à celles créées
        assertTrue(reservations.contains(reservation1));
        assertTrue(reservations.contains(reservation2));
    }

    @Test
    void testSave() {
        // Test pour vérifier la méthode save() héritée de JpaRepository
        City paris = new City();
        paris.setName("Paris");
        paris.setImage("url_image_paris");
        paris = cityRepository.save(paris);

        User user = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.USER, paris, null, userRepository);

        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, user);
        event = eventRepository.save(event);

        Pricing pricing = new Pricing();
        pricing.setName("Tarif standard");
        pricing.setPrice(50.0);
        pricing.setEvent(event);
        pricing = pricingRepository.save(pricing);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPricing(pricing);

        Reservation savedReservation = reservationRepository.save(reservation);

        assertNotNull(savedReservation.getId());
        assertEquals(user.getId(), savedReservation.getUser().getId());
        assertEquals(pricing.getId(), savedReservation.getPricing().getId());
    }

    @Test
    void testFindById() {
        // Test pour vérifier la méthode findById() héritée de JpaRepository
        City paris = new City();
        paris.setName("Paris");
        paris.setImage("url_image_paris");
        paris = cityRepository.save(paris);

        User user = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.USER, paris, null, userRepository);

        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, user);
        event = eventRepository.save(event);

        Pricing pricing = new Pricing();
        pricing.setName("Tarif standard");
        pricing.setPrice(50.0);
        pricing.setEvent(event);
        pricing = pricingRepository.save(pricing);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPricing(pricing);
        Reservation savedReservation = reservationRepository.save(reservation);

        Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());

        assertTrue(foundReservation.isPresent());
        assertEquals(savedReservation.getId(), foundReservation.get().getId());
        assertEquals(user.getId(), foundReservation.get().getUser().getId());
        assertEquals(pricing.getId(), foundReservation.get().getPricing().getId());
    }

    @Test
    void testDeleteById() {
        // Test pour vérifier la méthode deleteById() héritée de JpaRepository
        City paris = new City();
        paris.setName("Paris");
        paris.setImage("url_image_paris");
        paris = cityRepository.save(paris);

        User user = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.USER, paris, null, userRepository);

        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, user);
        event = eventRepository.save(event);

        Pricing pricing = new Pricing();
        pricing.setName("Tarif standard");
        pricing.setPrice(50.0);
        pricing.setEvent(event);
        pricing = pricingRepository.save(pricing);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPricing(pricing);
        reservation = reservationRepository.save(reservation);

        Long reservationId = reservation.getId();

        // Vérifier que la réservation existe avant la suppression
        assertTrue(reservationRepository.findById(reservationId).isPresent());

        // Supprimer la réservation
        reservationRepository.deleteById(reservationId);

        // Vérifier que la réservation n'existe plus après la suppression
        assertFalse(reservationRepository.findById(reservationId).isPresent());
    }

    @Test
    void testFindByUserId() {
        // Test pour vérifier la méthode personnalisée findByUserId()
        City paris = new City();
        paris.setName("Paris");
        paris.setImage("url_image_paris");
        paris = cityRepository.save(paris);

        User user = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.USER, paris, null, userRepository);

        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, user);
        event = eventRepository.save(event);

        Pricing pricing = new Pricing();
        pricing.setName("Tarif standard");
        pricing.setPrice(50.0);
        pricing.setEvent(event);
        pricing = pricingRepository.save(pricing);

        Reservation reservation1 = new Reservation();
        reservation1.setUser(user);
        reservation1.setPricing(pricing);
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setUser(user);
        reservation2.setPricing(pricing);
        reservationRepository.save(reservation2);

        List<Reservation> userReservations = reservationRepository.findByUserId(user.getId());

        assertNotNull(userReservations);
        assertEquals(2, userReservations.size());
        assertTrue(userReservations.stream().allMatch(r -> r.getUser().getId().equals(user.getId())));
    }
}
