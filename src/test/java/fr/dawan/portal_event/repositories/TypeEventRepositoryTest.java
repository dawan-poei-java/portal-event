package fr.dawan.portal_event.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import fr.dawan.portal_event.entities.TypeEvent;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TypeEventRepositoryTest {

    @Autowired
    private TypeEventRepository typeEventRepository;

    @Test
    public void testSaveTypeEvent() {
        // Créer un nouvel objet TypeEvent
        TypeEvent typeEvent = new TypeEvent();
        typeEvent.setName("Conférence");

        // Sauvegarder l'objet dans la base de données
        TypeEvent savedTypeEvent = typeEventRepository.save(typeEvent);

        // Vérifier que l'objet a été sauvegardé avec succès
        assertNotNull(savedTypeEvent.getId());
        assertEquals("Conférence", savedTypeEvent.getName());

        // Récupérer l'objet de la base de données
        TypeEvent retrievedTypeEvent = typeEventRepository.findById(savedTypeEvent.getId()).orElse(null);

        // Vérifier que l'objet récupéré correspond à celui sauvegardé
        assertNotNull(retrievedTypeEvent);
        assertEquals(savedTypeEvent.getId(), retrievedTypeEvent.getId());
        assertEquals("Conférence", retrievedTypeEvent.getName());
    }

    @Test
    public void testFindTypeEventById() {
        // Créer un nouvel objet TypeEvent
        TypeEvent typeEvent = new TypeEvent();
        typeEvent.setName("Séminaire");

        // Sauvegarder l'objet dans la base de données
        TypeEvent savedTypeEvent = typeEventRepository.save(typeEvent);

        // Récupérer l'objet de la base de données par son ID
        TypeEvent retrievedTypeEvent = typeEventRepository.findById(savedTypeEvent.getId()).orElse(null);

        // Vérifier que l'objet récupéré n'est pas null
        assertNotNull(retrievedTypeEvent);

        // Vérifier que l'objet récupéré correspond à celui sauvegardé
        assertEquals(savedTypeEvent.getId(), retrievedTypeEvent.getId());
        assertEquals("Séminaire", retrievedTypeEvent.getName());
    }

    @Test
    public void testFindAllTypeEvents() {
        // Créer plusieurs objets TypeEvent
        TypeEvent typeEvent1 = new TypeEvent();
        typeEvent1.setName("Conférence");
        TypeEvent typeEvent2 = new TypeEvent();
        typeEvent2.setName("Séminaire");
        TypeEvent typeEvent3 = new TypeEvent();
        typeEvent3.setName("Atelier");

        // Sauvegarder les objets dans la base de données
        typeEventRepository.saveAll(Arrays.asList(typeEvent1, typeEvent2, typeEvent3));

        // Récupérer tous les TypeEvent de la base de données
        List<TypeEvent> retrievedTypeEvents = typeEventRepository.findAll();

        // Vérifier que la liste n'est pas vide
        assertFalse(retrievedTypeEvents.isEmpty());

        // Vérifier que la liste contient le bon nombre d'éléments
        assertEquals(3, retrievedTypeEvents.size());

        // Vérifier que tous les éléments attendus sont présents
        assertTrue(retrievedTypeEvents.stream().anyMatch(te -> "Conférence".equals(te.getName())));
        assertTrue(retrievedTypeEvents.stream().anyMatch(te -> "Séminaire".equals(te.getName())));
        assertTrue(retrievedTypeEvents.stream().anyMatch(te -> "Atelier".equals(te.getName())));
    }

    @Test
    public void testUpdateTypeEvent() {
        // Créer un nouvel objet TypeEvent
        TypeEvent typeEvent = new TypeEvent();
        typeEvent.setName("Conférence");

        // Sauvegarder l'objet dans la base de données
        TypeEvent savedTypeEvent = typeEventRepository.save(typeEvent);

        // Modifier le nom de l'événement
        savedTypeEvent.setName("Séminaire");

        // Mettre à jour l'objet dans la base de données
        TypeEvent updatedTypeEvent = typeEventRepository.save(savedTypeEvent);

        // Récupérer l'objet mis à jour de la base de données
        TypeEvent retrievedTypeEvent = typeEventRepository.findById(updatedTypeEvent.getId()).orElse(null);

        // Vérifier que l'objet récupéré n'est pas null
        assertNotNull(retrievedTypeEvent);

        // Vérifier que le nom a été correctement mis à jour
        assertEquals("Séminaire", retrievedTypeEvent.getName());

        // Vérifier que l'ID n'a pas changé
        assertEquals(savedTypeEvent.getId(), retrievedTypeEvent.getId());
    }

    @Test
    public void testDeleteTypeEvent() {
        // Créer un nouvel objet TypeEvent
        TypeEvent typeEvent = new TypeEvent();
        typeEvent.setName("Conférence");

        // Sauvegarder l'objet dans la base de données
        TypeEvent savedTypeEvent = typeEventRepository.save(typeEvent);

        // Vérifier que l'objet a été sauvegardé
        assertNotNull(savedTypeEvent.getId());

        // Supprimer l'objet de la base de données
        typeEventRepository.delete(savedTypeEvent);

        // Essayer de récupérer l'objet supprimé
        Optional<TypeEvent> deletedTypeEvent = typeEventRepository.findById(savedTypeEvent.getId());

        // Vérifier que l'objet n'existe plus dans la base de données
        assertFalse(deletedTypeEvent.isPresent());
    }
}
