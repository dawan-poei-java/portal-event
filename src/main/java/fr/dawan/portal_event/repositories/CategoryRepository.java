package fr.dawan.portal_event.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
