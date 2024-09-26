package fr.dawan.portal_event.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.repositories.CityRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void run(String... args) throws Exception {
        if(cityRepository.count() == 0) {
            cityRepository.save(new City("Paris"));
            cityRepository.save(new City("Marseille"));
            cityRepository.save(new City("Lyon"));
            cityRepository.save(new City("Toulouse"));
            cityRepository.save(new City("Nice"));
            cityRepository.save(new City("Nantes"));
            cityRepository.save(new City("Strasbourg"));
            cityRepository.save(new City("Montpellier"));
            cityRepository.save(new City("Bordeaux"));
            cityRepository.save(new City("Lille"));
            cityRepository.save(new City("Rennes"));
            cityRepository.save(new City("Reims"));
            cityRepository.save(new City("Saint-Étienne"));
            cityRepository.save(new City("Le Havre"));
            cityRepository.save(new City("Toulon"));
            cityRepository.save(new City("Grenoble"));
            cityRepository.save(new City("Dijon"));
            cityRepository.save(new City("Nîmes"));
            cityRepository.save(new City("Aix-en-Provence"));
            cityRepository.save(new City("Saint-Denis"));
        }
    }

}
