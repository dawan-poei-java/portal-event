package fr.dawan.portal_event.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.utils.TestUtils;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testExistsByEmail() {

        // Créer une nouvelle ville
        City city = new City();
        city.setName("Paris");
        city.setImage("url_paris");
        city = cityRepository.save(city);

        // Créer un nouvel utilisateur
        User user = TestUtils.createFullUser("John", "Doe", "john.doe@example.com", UserRole.ORGANIZER, city, null, userRepository);
        userRepository.save(user);

        // Vérifier que l'email existe
        boolean exists = userRepository.existsByEmail("john.doe@example.com");
        assertTrue(exists);

        // Vérifier qu'un email inexistant ne renvoie pas true
        boolean notExists = userRepository.existsByEmail("nonexistent@example.com");
        assertFalse(notExists);
    }

    @Test
    public void testFindByEmail() {
        // Créer une nouvelle ville
        City ville = new City();
        ville.setName("Lyon");
        ville.setImage("url_lyon");
        ville = cityRepository.save(ville);

        // Créer un nouvel utilisateur
        User utilisateur = TestUtils.createFullUser("Jane", "Doe", "jane.doe@example.com", UserRole.USER, ville, null, userRepository);
        userRepository.save(utilisateur);

        // Rechercher l'utilisateur par email
        User utilisateurTrouve = userRepository.findByEmail("jane.doe@example.com");

        // Vérifier que l'utilisateur a été trouvé
        assertTrue(utilisateurTrouve != null);

        // Vérifier que les détails de l'utilisateur sont corrects
        assertEquals("Jane", utilisateurTrouve.getFirstName());
        assertEquals("Doe", utilisateurTrouve.getLastName());
        assertEquals(UserRole.USER, utilisateurTrouve.getUserRole());
        assertEquals("Lyon", utilisateurTrouve.getCity().getName());

        // Vérifier qu'un email inexistant ne renvoie pas d'utilisateur
        User utilisateurInexistant = userRepository.findByEmail("inexistant@example.com");
        assertNull(utilisateurInexistant);
    }

    @Test
    public void testSaveUser() {
        // Créer une nouvelle ville
        City ville = new City();
        ville.setName("Marseille");
        ville.setImage("url_marseille");
        ville = cityRepository.save(ville);

        // Créer un nouvel utilisateur
        User utilisateur = TestUtils.createFullUser("Alice", "Martin", "alice.martin@example.com", UserRole.ADMIN, ville, null, userRepository);

        // Sauvegarder l'utilisateur
        User utilisateurSauvegarde = userRepository.save(utilisateur);

        // Vérifier que l'utilisateur a été sauvegardé avec succès
        assertNotNull(utilisateurSauvegarde.getId());
        assertEquals("Alice", utilisateurSauvegarde.getFirstName());
        assertEquals("Martin", utilisateurSauvegarde.getLastName());
        assertEquals("alice.martin@example.com", utilisateurSauvegarde.getEmail());
        assertEquals(UserRole.ADMIN, utilisateurSauvegarde.getUserRole());
        assertEquals("Marseille", utilisateurSauvegarde.getCity().getName());

        // Récupérer l'utilisateur de la base de données
        User utilisateurRecupere = userRepository.findById(utilisateurSauvegarde.getId()).orElse(null);

        // Vérifier que l'utilisateur récupéré correspond à celui sauvegardé
        assertNotNull(utilisateurRecupere);
        assertEquals(utilisateurSauvegarde.getId(), utilisateurRecupere.getId());
        assertEquals("Alice", utilisateurRecupere.getFirstName());
        assertEquals("Martin", utilisateurRecupere.getLastName());
        assertEquals("alice.martin@example.com", utilisateurRecupere.getEmail());
        assertEquals(UserRole.ADMIN, utilisateurRecupere.getUserRole());
        assertEquals("Marseille", utilisateurRecupere.getCity().getName());
    }

    @Test
    public void testFindUserById() {
        // Créer une nouvelle ville
        City ville = new City();
        ville.setName("Paris");
        ville.setImage("url_paris");
        ville = cityRepository.save(ville);

        // Créer un nouvel utilisateur
        User utilisateur = TestUtils.createFullUser("Pierre", "Dupont", "pierre.dupont@example.com", UserRole.USER, ville, null, userRepository);

        // Sauvegarder l'utilisateur
        User utilisateurSauvegarde = userRepository.save(utilisateur);

        // Récupérer l'utilisateur par son ID
        User utilisateurRecupere = userRepository.findById(utilisateurSauvegarde.getId()).orElse(null);

        // Vérifier que l'utilisateur récupéré n'est pas null
        assertNotNull(utilisateurRecupere);

        // Vérifier que les détails de l'utilisateur récupéré correspondent à ceux sauvegardés
        assertEquals(utilisateurSauvegarde.getId(), utilisateurRecupere.getId());
        assertEquals("Pierre", utilisateurRecupere.getFirstName());
        assertEquals("Dupont", utilisateurRecupere.getLastName());
        assertEquals("pierre.dupont@example.com", utilisateurRecupere.getEmail());
        assertEquals(UserRole.USER, utilisateurRecupere.getUserRole());
        assertEquals("Paris", utilisateurRecupere.getCity().getName());

        // Tester avec un ID inexistant
        User utilisateurInexistant = userRepository.findById(9999L).orElse(null);
        assertNull(utilisateurInexistant);
    }

    @Test
    public void testDeleteUser() {
        // Créer une nouvelle ville
        City ville = new City();
        ville.setName("Lyon");
        ville.setImage("url_lyon");
        ville = cityRepository.save(ville);

        // Créer un nouvel utilisateur
        User utilisateur = TestUtils.createFullUser("Jean", "Dupont", "jean.dupont@example.com", UserRole.USER, ville, null, userRepository);

        // Sauvegarder l'utilisateur
        User utilisateurSauvegarde = userRepository.save(utilisateur);

        // Vérifier que l'utilisateur existe dans la base de données
        assertNotNull(userRepository.findById(utilisateurSauvegarde.getId()).orElse(null));

        // Supprimer l'utilisateur
        userRepository.delete(utilisateurSauvegarde);

        // Vérifier que l'utilisateur a été supprimé
        assertNull(userRepository.findById(utilisateurSauvegarde.getId()).orElse(null));

        // Vérifier que la ville n'a pas été supprimée (intégrité référentielle)
        assertNotNull(cityRepository.findById(ville.getId()).orElse(null));
    }
}
