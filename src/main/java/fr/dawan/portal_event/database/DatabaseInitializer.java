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
            cityRepository.save(new City("Paris", "https://unsplash.com/fr/photos/eiffel-tower-paris-france-XSl6VEp7LFg"));
            cityRepository.save(new City("Marseille", "https://images.pexels.com/photos/11690121/pexels-photo-11690121.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
            cityRepository.save(new City("Toulouse", "https://plus.unsplash.com/premium_photo-1697730048830-03482bd51e10?q=80&w=2751&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
            cityRepository.save(new City("Nice", "https://images.unsplash.com/photo-1618079167568-f480a967d4c3?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
            cityRepository.save(new City("Nantes", "https://metropole.nantes.fr/files/images/vie-institutions/villes/nantes-vue-aerienne-1920.jpg"));
            cityRepository.save(new City("Strasbourg", "https://img.lonelyplanet.fr/s3fs-public/styles/wide/public/import/destination/slider/descktop/istock-1070076444.jpg.webp?itok=NKLsSR8I"));
            cityRepository.save(new City("Montpellier", "https://www.montpellier.fr/uploads/Image/70/IMF_COLONNE_RESPONSIVE/GAB_MPL/29354_083_banner-ville-Montpellier.jpg"));
            cityRepository.save(new City("Bordeaux", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Bordeaux_Place_de_la_Bourse_de_nuit.jpg/1920px-Bordeaux_Place_de_la_Bourse_de_nuit.jpg"));
            cityRepository.save(new City("Lille", "https://www.lilletourism.com/app/uploads/lille-tourisme/2023/09/thumbs/Grand-Place-Lille-Tourisme-23-min-1920x960-crop-1695644063.jpg"));
            cityRepository.save(new City("Rennes", "https://www.rennes-business.com/uploads/sites/2/2019/02/rennes-ville-attractive.jpg"));
            cityRepository.save(new City("Reims", "https://i-de.unimedias.fr/2023/12/07/dethemis0904757-6571e466a6d1c.jpg?auto=format%2Ccompress&crop=faces&cs=tinysrgb&fit=max&w=1050"));
            cityRepository.save(new City("Saint-Étienne", "https://www.sncf-connect.com/assets/media/2022-04/place-jean-jaures-saint-etienne.jpg"));
            cityRepository.save(new City("Le Havre", "https://i-de.unimedias.fr/2023/12/07/dt158lehavrevuemerbr-6571e2b88e9bb.jpg?auto=format%2Ccompress&crop=faces&cs=tinysrgb&fit=crop&h=501&w=890"));
            cityRepository.save(new City("Toulon", "https://toulon.fr/sites/new.toulon.fr/files/2017-08-09-alb-0038.jpg"));
            cityRepository.save(new City("Grenoble", "https://www.gix-immobilier.fr/public/files/images/AdobeStock_75656482.jpeg"));
            cityRepository.save(new City("Dijon", "https://i0.wp.com/blog.mappy.com/wp-content/uploads/2023/04/0b98e-istock-1074291938-blog.png?fit=1100%2C500&ssl=1"));
            cityRepository.save(new City("Nîmes", "https://images.winalist.com/blog/wp-content/uploads/2021/05/26144008/AdobeStock_277499536.jpeg"));
            cityRepository.save(new City("Aix-en-Provence", "https://www.aixenprovence.fr/local/cache-gd2/1a/43e306225ecfbf56da657aa05c9b04.jpg?1710250789"));
            cityRepository.save(new City("Saint-Denis", "https://www.tourisme93.com/Local/tourisme93/files/10515/s550-CMN_res_plw15_0014.jpg"));
        }
    }

}
