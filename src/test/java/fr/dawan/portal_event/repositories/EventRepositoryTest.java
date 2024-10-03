package fr.dawan.portal_event.repositories;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.EventState;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    

    @Test
    void testFindUpcomingEvents() {
        // Simuler une ville pour les événements
        City paris = new City();
        paris.setId(1L); // Simuler un ID
        paris.setName("Paris");

        // Simuler un organisateur
        User organisateur = new User();
        organisateur.setId(2L); // Simuler un ID
        organisateur.setFirstName("organisateur1");

        // Créer des événements avec des dates futures et passées
        Event eventPasse = createFullEvent("Événement passé", LocalDateTime.now().minusDays(1), paris, organisateur);
        eventRepository.save(eventPasse);

        Event eventFutur1 = createFullEvent("Événement futur 1", LocalDateTime.now().plusDays(1), paris, organisateur);
        eventRepository.save(eventFutur1);

        Event eventFutur2 = createFullEvent("Événement futur 2", LocalDateTime.now().plusDays(2), paris, organisateur);
        eventRepository.save(eventFutur2);

        // Récupérer les événements à venir
        List<Event> upcomingEvents = eventRepository.findUpcomingEvents();

        // Vérifier que seuls les événements futurs sont retournés
        assertTrue(upcomingEvents.size() >= 2);
        assertTrue(upcomingEvents.stream().anyMatch(e -> e.getTitle().equals("Événement futur 1")));
        assertTrue(upcomingEvents.stream().anyMatch(e -> e.getTitle().equals("Événement futur 2")));
        assertFalse(upcomingEvents.stream().anyMatch(e -> e.getTitle().equals("Événement passé")));
    }

    @Test
    void testFindAllByCityId() {
        // Créer une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");

        // Créer un organisateur
        User organisateur = new User();
        organisateur.setId(2L);
        organisateur.setFirstName("organisateur1");

        // Créer des événements pour cette ville
        Event event1 = createFullEvent("Événement Paris 1", LocalDateTime.now().plusDays(1), paris, organisateur);
        Event event2 = createFullEvent("Événement Paris 2", LocalDateTime.now().plusDays(2), paris, organisateur);
        eventRepository.save(event1);
        eventRepository.save(event2);

        // Créer une autre ville et un événement associé
        City lyon = new City();
        lyon.setId(3L);
        lyon.setName("Lyon");
        Event eventLyon = createFullEvent("Événement Lyon", LocalDateTime.now().plusDays(3), lyon, organisateur);
        eventRepository.save(eventLyon);

        // Récupérer les événements pour Paris
        List<Event> eventsInParis = eventRepository.findAllByCityId(paris.getId());

        // Vérifications
        assertTrue(eventsInParis.size() >= 2);
        assertTrue(eventsInParis.stream().allMatch(e -> e.getCity().getId().equals(paris.getId())));
        assertTrue(eventsInParis.stream().anyMatch(e -> e.getTitle().equals("Événement Paris 1")));
        assertTrue(eventsInParis.stream().anyMatch(e -> e.getTitle().equals("Événement Paris 2")));
        assertFalse(eventsInParis.stream().anyMatch(e -> e.getTitle().equals("Événement Lyon")));
    }

    @Test
    void testFindMostPopularEvent() {

    }

    @Test
    void testFindEventByIdAndCityName() {
        // À implémenter
    }

    @Test
    void testFindEventsByOrganizerId() {
        // À implémenter
    }

    @Test
    void testSaveEvent() {
        // À implémenter
    }

    @Test
    void testDeleteEvent() {
        // À implémenter
    }

    private Event createFullEvent(String title, LocalDateTime startDate, City city, User organizer) {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription("Description de " + title);
        event.setStartDate(startDate);
        event.setEndDate(startDate.plusHours(2));
        event.setStartTime(startDate.toLocalTime());
        event.setEndTime(startDate.plusHours(2).toLocalTime());
        event.setAddress("123 Rue Example");
        event.setAddressComplement("Étage 4");
        event.setCity(city);
        event.setZipCode("75000");
        event.setOrganizer(organizer);
        event.setState(EventState.WAITING);
        return event;
    }
}
