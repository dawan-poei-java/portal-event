package fr.dawan.portal_event.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.portal_event.entities.Category;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveCategory() {
        // Créer une nouvelle catégorie
        Category category = new Category();
        category.setName("Test Category");

        // Sauvegarder la catégorie
        Category savedCategory = categoryRepository.save(category);

        // Vérifier que la catégorie a été sauvegardée avec succès
        assertNotNull(savedCategory.getId());
        assertEquals("Test Category", savedCategory.getName());

        // Récupérer la catégorie depuis la base de données
        Category retrievedCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);

        // Vérifier que la catégorie récupérée correspond à celle sauvegardée
        assertNotNull(retrievedCategory);
        assertEquals(savedCategory.getId(), retrievedCategory.getId());
        assertEquals(savedCategory.getName(), retrievedCategory.getName());
    }

    @Test
    public void testFindCategoryById() {
        // Créer une nouvelle catégorie
        Category category = new Category();
        category.setName("Test Category");
        Category savedCategory = categoryRepository.save(category);

        // Récupérer la catégorie par son ID
        Category foundCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);

        // Vérifier que la catégorie a été trouvée et correspond à celle sauvegardée
        assertNotNull(foundCategory);
        assertEquals(savedCategory.getId(), foundCategory.getId());
        assertEquals(savedCategory.getName(), foundCategory.getName());
    }

    @Test
    public void testFindAllCategories() {
        // Créer plusieurs catégories
        Category category1 = new Category();
        category1.setName("Catégorie 1");
        Category category2 = new Category();
        category2.setName("Catégorie 2");
        Category category3 = new Category();
        category3.setName("Catégorie 3");

        // Sauvegarder les catégories
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // Récupérer toutes les catégories
        List<Category> allCategories = categoryRepository.findAll();

        // Vérifier que toutes les catégories ont été récupérées
        assertTrue(allCategories.size() >= 3);
        assertTrue(allCategories.stream().anyMatch(c -> c.getName().equals("Catégorie 1")));
        assertTrue(allCategories.stream().anyMatch(c -> c.getName().equals("Catégorie 2")));
        assertTrue(allCategories.stream().anyMatch(c -> c.getName().equals("Catégorie 3")));
    }

    @Test
    public void testUpdateCategory() {
        // Créer une nouvelle catégorie
        Category category = new Category();
        category.setName("Catégorie initiale");
        Category savedCategory = categoryRepository.save(category);

        // Modifier la catégorie
        savedCategory.setName("Catégorie modifiée");
        Category updatedCategory = categoryRepository.save(savedCategory);

        // Récupérer la catégorie mise à jour
        Category retrievedCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);

        // Vérifier que la catégorie a été correctement mise à jour
        assertNotNull(retrievedCategory);
        assertEquals(savedCategory.getId(), retrievedCategory.getId());
        assertEquals("Catégorie modifiée", retrievedCategory.getName());
    }

    @Test
    public void testDeleteCategory() {
        // Créer une nouvelle catégorie
        Category category = new Category();
        category.setName("Catégorie à supprimer");
        Category savedCategory = categoryRepository.save(category);

        // Vérifier que la catégorie a été sauvegardée
        assertNotNull(savedCategory.getId());

        // Supprimer la catégorie
        categoryRepository.deleteById(savedCategory.getId());

        // Essayer de récupérer la catégorie supprimée
        Optional<Category> deletedCategory = categoryRepository.findById(savedCategory.getId());

        // Vérifier que la catégorie n'existe plus
        assertTrue(deletedCategory.isEmpty());
    }


}
