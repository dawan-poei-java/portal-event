package fr.dawan.portal_event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.portal_event.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
