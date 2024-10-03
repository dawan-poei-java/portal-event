package fr.dawan.portal_event.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.entities.Pricing;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.utils.TestUtils;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
public class PricingRepositoryTest {

    @Autowired
    private PricingRepository pricingRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testSavePricing() {
        // Créer un nouvel objet Pricing
        Pricing pricing = new Pricing();
        pricing.setName("Tarif standard");
        pricing.setPrice(50.0);
        
        // Créer une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);

        // Créer un organisateur
        User organizer = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer un événement complet
        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, organizer);
        event = eventRepository.save(event);
        
        // Associer le pricing à l'événement
        pricing.setEvent(event);
        // Sauvegarder le pricing
        Pricing savedPricing = pricingRepository.save(pricing);
        
        // Vérifications
        assertNotNull(savedPricing.getId());
        assertEquals("Tarif standard", savedPricing.getName());
        assertEquals(50.0, savedPricing.getPrice(), 0.001);
        
        // Récupérer le pricing sauvegardé
        Optional<Pricing> retrievedPricingOptional = pricingRepository.findById(savedPricing.getId());
        assertTrue(retrievedPricingOptional.isPresent());
        
        Pricing retrievedPricing = retrievedPricingOptional.get();
        assertEquals(savedPricing.getId(), retrievedPricing.getId());
        assertEquals("Tarif standard", retrievedPricing.getName());
        assertEquals(50.0, retrievedPricing.getPrice(), 0.001);
    }

    @Test
    public void testFindPricingById() {
        // Créer une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);

        // Créer un organisateur
        User organizer = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer un événement
        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, organizer);
        event = eventRepository.save(event);

        // Créer un pricing
        Pricing pricing = new Pricing();
        pricing.setName("Tarif VIP");
        pricing.setPrice(100.0);
        pricing.setEvent(event);
        Pricing savedPricing = pricingRepository.save(pricing);

        // Rechercher le pricing par son ID
        Optional<Pricing> foundPricingOptional = pricingRepository.findById(savedPricing.getId());

        // Vérifications
        assertTrue(foundPricingOptional.isPresent());
        Pricing foundPricing = foundPricingOptional.get();
        assertEquals(savedPricing.getId(), foundPricing.getId());
        assertEquals("Tarif VIP", foundPricing.getName());
        assertEquals(100.0, foundPricing.getPrice(), 0.001);
        assertEquals(event.getId(), foundPricing.getEvent().getId());

        // Tester avec un ID inexistant
        Optional<Pricing> notFoundPricing = pricingRepository.findById(9999L);
        assertTrue(notFoundPricing.isEmpty());
    }

    @Test
    public void testFindAllPricings() {
        // Créer une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);

        // Créer un organisateur
        User organizer = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer un événement
        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, organizer);
        event = eventRepository.save(event);

        // Créer plusieurs tarifs
        Pricing pricing1 = new Pricing();
        pricing1.setName("Tarif Standard");
        pricing1.setPrice(50.0);
        pricing1.setEvent(event);

        Pricing pricing2 = new Pricing();
        pricing2.setName("Tarif VIP");
        pricing2.setPrice(100.0);
        pricing2.setEvent(event);

        Pricing pricing3 = new Pricing();
        pricing3.setName("Tarif Étudiant");
        pricing3.setPrice(25.0);
        pricing3.setEvent(event);

        // Sauvegarder les tarifs
        pricingRepository.saveAll(List.of(pricing1, pricing2, pricing3));

        // Récupérer tous les tarifs
        List<Pricing> allPricings = pricingRepository.findAll();

        // Vérifications
        assertNotNull(allPricings);
        assertTrue(allPricings.size() >= 3);
        assertTrue(allPricings.stream().anyMatch(p -> p.getName().equals("Tarif Standard") && p.getPrice() == 50.0));
        assertTrue(allPricings.stream().anyMatch(p -> p.getName().equals("Tarif VIP") && p.getPrice() == 100.0));
        assertTrue(allPricings.stream().anyMatch(p -> p.getName().equals("Tarif Étudiant") && p.getPrice() == 25.0));
    }

    @Test
    public void testUpdatePricing() {
        // Créer une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);

        // Créer un organisateur
        User organizer = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer un événement
        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, organizer);
        event = eventRepository.save(event);

        // Créer un tarif initial
        Pricing initialPricing = new Pricing();
        initialPricing.setName("Tarif Initial");
        initialPricing.setPrice(50.0);
        initialPricing.setEvent(event);
        initialPricing = pricingRepository.save(initialPricing);

        // Modifier le tarif
        initialPricing.setName("Tarif Modifié");
        initialPricing.setPrice(75.0);
        pricingRepository.save(initialPricing);

        // Récupérer le tarif mis à jour
        Pricing updatedPricing = pricingRepository.findById(initialPricing.getId()).orElse(null);

        // Vérifications
        assertNotNull(updatedPricing);
        assertEquals("Tarif Modifié", updatedPricing.getName());
        assertEquals(75.0, updatedPricing.getPrice(), 0.01);
        assertEquals(event.getId(), updatedPricing.getEvent().getId());
    }

    @Test
    public void testDeletePricing() {
        // Créer une ville
        City paris = new City();
        paris.setId(1L);
        paris.setName("Paris");
        paris.setImage("url_paris");
        paris = cityRepository.save(paris);

        // Créer un organisateur
        User organizer = TestUtils.createFullUser("John", "Doe", "john@example.com", UserRole.ORGANIZER, paris, null, userRepository);

        // Créer un événement
        Event event = TestUtils.createFullEvent("Événement Test", LocalDateTime.now().plusDays(7), paris, organizer);
        event = eventRepository.save(event);

        // Créer un tarif
        Pricing pricing = new Pricing();
        pricing.setName("Tarif à supprimer");
        pricing.setPrice(30.0);
        pricing.setEvent(event);
        pricing = pricingRepository.save(pricing);

        // Vérifier que le tarif a été créé
        assertNotNull(pricing.getId());

        // Supprimer le tarif
        pricingRepository.deleteById(pricing.getId());

        // Vérifier que le tarif a été supprimé
        Optional<Pricing> deletedPricing = pricingRepository.findById(pricing.getId());
        assertTrue(deletedPricing.isEmpty());
    }
}
