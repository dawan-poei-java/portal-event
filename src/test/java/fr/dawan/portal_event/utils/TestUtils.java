package fr.dawan.portal_event.utils;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.EventState;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestUtils {

    public static Event createFullEvent(String title, LocalDateTime startDate, City city, User organizer) {
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

    public static User createFullUser(String firstName, String lastName, String email, UserRole role, City city, String phoneNumber, UserRepository userRepository) {
        if (phoneNumber == null) {
            phoneNumber = "0123456789";
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword("motdepasse123");
        user.setPhoneNumber(phoneNumber);
        user.setUserRole(role);
        user.setCreatedAt(LocalDateTime.now());
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAddress("123 Rue de la République");
        user.setAddressComplement("Appartement 4B");
        user.setZipCode("75001");
        user.setCity(city);
        return userRepository.saveAndFlush(user);
    }
}
