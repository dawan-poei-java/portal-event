package fr.dawan.portal_event.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.portal_event.entities.City;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testCreateCity(){
        // Créer une nouvelle ville
        City ville = new City();
        ville.setName("Paris");
        ville.setImage("url");

        // Sauvegarder la ville
        City villeSauvegardee = cityRepository.save(ville);

        // Vérifier que la ville a été sauvegardée avec succès
        assertNotNull(villeSauvegardee.getId());
        assertEquals("Paris", villeSauvegardee.getName());

        // Récupérer la ville depuis la base de données
        City villeRecuperee = cityRepository.findById(villeSauvegardee.getId()).orElse(null);

        // Vérifier que la ville récupérée correspond à celle sauvegardée
        assertNotNull(villeRecuperee);
        assertEquals(villeSauvegardee.getId(), villeRecuperee.getId());
        assertEquals(villeSauvegardee.getName(), villeRecuperee.getName());
    }
    @Test
    public void testFindCityByNameAndId(){
    // Créer une nouvelle ville
    City ville = new City();
    ville.setName("Tourcoing");
    ville.setImage("url");

    City villeSauvegardee = cityRepository.save(ville);

    // Rechercher la ville par son nom
    City villeParNom = cityRepository.findByName("Tourcoing");
    assertNotNull(villeParNom);
    assertEquals("Tourcoing", villeParNom.getName());

    // Rechercher la ville par son ID
    City villeParId = cityRepository.findById(villeSauvegardee.getId()).orElse(null);
    assertNotNull(villeParId);
    assertEquals(villeSauvegardee.getId(), villeParId.getId());
    assertEquals("Tourcoing", villeParId.getName());

    }
    @Test
    public void testFindAllCities(){
    // Créer plusieurs villes
    City ville1 = new City();
    ville1.setName("Paris");
    ville1.setImage("url");

    City ville2 = new City();
    ville2.setName("Lyon");
    ville2.setImage("url");

    City ville3 = new City();
    ville3.setName("Marseille");
    ville3.setImage("url");


    // Sauvegarder les villes
    cityRepository.save(ville1);
    cityRepository.save(ville2);
    cityRepository.save(ville3);

    // Récupérer toutes les villes
    List<City> toutesLesVilles = cityRepository.findAll();

    // Vérifier que toutes les villes ont été récupérées
    assertTrue(toutesLesVilles.size() >= 3);
    assertTrue(toutesLesVilles.stream().anyMatch(v -> v.getName().equals("Paris")));
    assertTrue(toutesLesVilles.stream().anyMatch(v -> v.getName().equals("Lyon")));
    assertTrue(toutesLesVilles.stream().anyMatch(v -> v.getName().equals("Marseille")));
    }
    @Test
    public void testUpdateCity(){
    // Créer une nouvelle ville
    City ville = new City();
    ville.setName("Bordeaux");
    ville.setImage("url");
    City villeSauvegardee = cityRepository.save(ville);

    // Modifier la ville
    villeSauvegardee.setName("Bordeaux-Métropole");
    City villeModifiee = cityRepository.save(villeSauvegardee);

    // Récupérer la ville mise à jour
    City villeRecuperee = cityRepository.findById(villeSauvegardee.getId()).orElse(null);

    // Vérifier que la ville a été correctement mise à jour
    assertNotNull(villeRecuperee);
    assertEquals(villeSauvegardee.getId(), villeRecuperee.getId());
    assertEquals("Bordeaux-Métropole", villeRecuperee.getName());

    }
    @Test
    public void testDeleteCity(){
    // Créer une nouvelle ville
    City ville = new City();
    ville.setName("Toulouse");
    ville.setImage("url");

    City villeSauvegardee = cityRepository.save(ville);

    // Vérifier que la ville a été sauvegardée
    assertNotNull(villeSauvegardee.getId());

    // Supprimer la ville
    cityRepository.deleteById(villeSauvegardee.getId());

    // Essayer de récupérer la ville supprimée
    Optional<City> villeSupprimee = cityRepository.findById(villeSauvegardee.getId());

    // Vérifier que la ville n'existe plus
    assertTrue(villeSupprimee.isEmpty());

    }
}
