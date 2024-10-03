package fr.dawan.portal_event.repositories;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.entities.Pricing;
import fr.dawan.portal_event.entities.Reservation;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.EventState;
import fr.dawan.portal_event.enums.UserRole;

import fr.dawan.portal_event.utils.TestUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Test
    void testFindUpcomingEvents() {
        // Créer et simuler la sauvegarde d'une ville
        City paris = new City();
        paris.setId(1L); // Définir un ID manuellement
        paris.setName("Paris");
        paris.setImage("url");
        paris = cityRepository.saveAndFlush(paris);

        // Créer et simuler la sauvegarde d'un organisateur
        User organisateur = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer et définir une ville pour l'utilisateur
        City userCity = new City();
        userCity.setId(2L);
        userCity.setName("Paris");
        userCity.setImage("url");
        cityRepository.saveAndFlush(userCity);

        organisateur.setCity(userCity);

        // Sauvegarder l'utilisateur
        organisateur = userRepository.saveAndFlush(organisateur);

        // Créer des événements avec des dates futures et passées
        Event eventPasse = TestUtils.createFullEvent("Événement passé", LocalDateTime.now().minusDays(1), paris, organisateur);
        eventRepository.saveAndFlush(eventPasse);

        Event eventFutur1 = TestUtils.createFullEvent("Événement futur 1", LocalDateTime.now().plusDays(1), paris, organisateur);
        eventRepository.saveAndFlush(eventFutur1);

        Event eventFutur2 = TestUtils.createFullEvent("Événement futur 2", LocalDateTime.now().plusDays(2), paris, organisateur);
        eventRepository.saveAndFlush(eventFutur2);

        // Récupérer les événements à venir
        List<Event> upcomingEvents = eventRepository.findUpcomingEvents();

        // Vérifications
        assertTrue(upcomingEvents.size() >= 2);
        assertTrue(upcomingEvents.stream().anyMatch(e -> e.getTitle().equals("Événement futur 1")));
        assertTrue(upcomingEvents.stream().anyMatch(e -> e.getTitle().equals("Événement futur 2")));
        assertFalse(upcomingEvents.stream().anyMatch(e -> e.getTitle().equals("Événement passé")));
    }

    @Test
    void testFindAllByCityId() {
        // Créer et sauvegarder une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.saveAndFlush(paris);

        // Créer et sauvegarder un organisateur
        User organisateur = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer et sauvegarder des événements pour Paris
        Event eventParis1 = TestUtils.createFullEvent("Événement Paris 1", LocalDateTime.now().plusDays(1), paris, organisateur);
        eventRepository.saveAndFlush(eventParis1);
        Event eventParis2 = TestUtils.createFullEvent("Événement Paris 2", LocalDateTime.now().plusDays(2), paris, organisateur);
        eventRepository.saveAndFlush(eventParis2);

        // Créer et sauvegarder une autre ville et un événement associé
        City lyon = new City();
        lyon.setId(2L);
        lyon.setName("Lyon");
        lyon.setImage("url_lyon");
        lyon = cityRepository.saveAndFlush(lyon);
        Event eventLyon = TestUtils.createFullEvent("Événement Lyon", LocalDateTime.now().plusDays(3), lyon, organisateur);
        eventRepository.saveAndFlush(eventLyon);

        // Récupérer les événements pour Paris
        List<Event> eventsInParis = eventRepository.findAllByCityId(paris.getId());

        // Vérifications
        assertEquals(2, eventsInParis.size());
        assertTrue(eventsInParis.stream().allMatch(e -> e.getCity().getName().equals("Paris")));
        assertTrue(eventsInParis.stream().anyMatch(e -> e.getTitle().equals("Événement Paris 1")));
        assertTrue(eventsInParis.stream().anyMatch(e -> e.getTitle().equals("Événement Paris 2")));
        assertFalse(eventsInParis.stream().anyMatch(e -> e.getTitle().equals("Événement Lyon")));
    }

    @Test
    void testFindMostPopularEvent() {
        // Simuler une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);

        // Simuler un organisateur
        User organisateur = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer plusieurs événements avec différentes popularités
        Event eventPopulaire = TestUtils.createFullEvent("Événement Populaire", LocalDateTime.now().plusDays(1), paris, organisateur);
        Event eventMoinsPopulaire = TestUtils.createFullEvent("Événement Moins Populaire", LocalDateTime.now().plusDays(2), paris, organisateur);

        // Initialiser les listes de réservations
        Pricing pricingPopulaire = new Pricing();
        pricingPopulaire.setEvent(eventPopulaire);
        Pricing pricingMoinsPopulaire = new Pricing();
        pricingMoinsPopulaire.setEvent(eventMoinsPopulaire);

        eventMoinsPopulaire.setPricings(new ArrayList<>());
        eventPopulaire.setPricings(new ArrayList<>());
        pricingPopulaire.setReservations(new ArrayList<>());
        pricingMoinsPopulaire.setReservations(new ArrayList<>());

        for (int i = 0; i < 20; i++) {
            Reservation reservation = new Reservation();
            reservation.setPricing(pricingPopulaire);
            pricingPopulaire.getReservations().add(reservation);
        }

        for (int i = 0; i < 5; i++) {
            Reservation reservation = new Reservation();
            reservation.setPricing(pricingMoinsPopulaire);
            pricingMoinsPopulaire.getReservations().add(reservation);
        }

        eventPopulaire.getPricings().add(pricingPopulaire);
        eventMoinsPopulaire.getPricings().add(pricingMoinsPopulaire);

        eventRepository.save(eventPopulaire);
        eventRepository.save(eventMoinsPopulaire);

        // Récupérer l'événement le plus populaire
        Event mostPopularEvent = eventRepository.findMostPopularEvent();

        // Vérifications
        assertNotNull(mostPopularEvent);
        assertEquals("Événement Populaire", mostPopularEvent.getTitle());
        assertTrue(mostPopularEvent.getPricings().stream()
                .flatMap(p -> p.getReservations().stream())
                .count() > eventMoinsPopulaire.getPricings().stream()
                .flatMap(p -> p.getReservations().stream())
                .count());

    }

    @Test
    void testFindEventByIdAndCityName() {
        // Créer une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);

        // Créer un organisateur
        User organizer = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer un événement
        Event event = TestUtils.createFullEvent("Événement à Paris", LocalDateTime.now().plusDays(7), paris, organizer);
        event = eventRepository.save(event);

        // Rechercher l'événement par son ID et l'ID de la ville
        Event foundEvent = eventRepository.findEventByIdAndCityName(event.getId(), paris.getId());

        // Vérifications
        assertNotNull(foundEvent);
        assertEquals("Événement à Paris", foundEvent.getTitle());
        assertEquals("Paris", foundEvent.getCity().getName());

        // Tester avec un ID d'événement inexistant
        Event notFoundEvent = eventRepository.findEventByIdAndCityName(9999L, paris.getId());
        assertNull(notFoundEvent);

        // Tester avec un ID de ville incorrect
        Event wrongCityEvent = eventRepository.findEventByIdAndCityName(event.getId(), 9999L);
        assertNull(wrongCityEvent);
    }

    @Test
    void testFindEventsByOrganizerId() {
        // Créer une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);

        // Créer deux organisateurs
        User organizer1 = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);
        User organizer2 = TestUtils.createFullUser("Jane", "Smith", "jane@example.com", UserRole.ORGANIZER, paris, "0606060606", userRepository);

        // Créer des événements pour chaque organisateur
        Event event1 = TestUtils.createFullEvent("Événement 1 de John", LocalDateTime.now().plusDays(7), paris, organizer1);
        Event event2 = TestUtils.createFullEvent("Événement 2 de John", LocalDateTime.now().plusDays(14), paris, organizer1);
        Event event3 = TestUtils.createFullEvent("Événement de Jane", LocalDateTime.now().plusDays(21), paris, organizer2);

        eventRepository.saveAll(List.of(event1, event2, event3));

        // Rechercher les événements pour l'organisateur 1
        List<Event> eventsOrganizer1 = eventRepository.findEventsByOrganizerId(organizer1.getId());

        // Vérifications
        assertNotNull(eventsOrganizer1);
        assertEquals(2, eventsOrganizer1.size());
        final long organizer1Id = organizer1.getId();
        assertTrue(eventsOrganizer1.stream().allMatch(e -> e.getOrganizer().getId().equals(organizer1Id)));

        // Rechercher les événements pour l'organisateur 2
        List<Event> eventsOrganizer2 = eventRepository.findEventsByOrganizerId(organizer2.getId());

        // Vérifications
        assertNotNull(eventsOrganizer2);
        assertEquals(1, eventsOrganizer2.size());
        final long organizer2Id = organizer2.getId();
        assertTrue(eventsOrganizer2.stream().allMatch(e -> e.getOrganizer().getId().equals(organizer2Id)));

        // Tester avec un ID d'organisateur inexistant
        List<Event> noEvents = eventRepository.findEventsByOrganizerId(9999L);
        assertTrue(noEvents.isEmpty());
    }

    @Test
    void testSaveEvent() {
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);
        User organizer = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);
        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, organizer);
        eventRepository.save(event);

    // Vérifier que l'événement a été correctement sauvegardé
    assertNotNull(event.getId());
    
    // Récupérer l'événement sauvegardé depuis la base de données
    Optional<Event> savedEventOptional = eventRepository.findById(event.getId());
    assertTrue(savedEventOptional.isPresent());
    
    Event savedEvent = savedEventOptional.get();
    assertEquals("Événement Test", savedEvent.getTitle());
    assertEquals(paris, savedEvent.getCity());
    assertEquals(organizer, savedEvent.getOrganizer());
    assertEquals(LocalDateTime.now().plusDays(7).toLocalDate(), savedEvent.getStartDate().toLocalDate());
    }

    @Test
    void testDeleteEvent() {
        // Créer un événement
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);
        User organizer = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);
        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, organizer);
        eventRepository.save(event);

        // Vérifier que l'événement est bien enregistré
        Long eventId = event.getId();
        assertNotNull(eventId);

        // Supprimer l'événement
        eventRepository.deleteById(eventId);

        // Vérifier que l'événement a bien été supprimé
        Optional<Event> deletedEvent = eventRepository.findById(eventId);
        assertTrue(deletedEvent.isEmpty());
    }

}