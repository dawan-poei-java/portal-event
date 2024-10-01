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
            cityRepository.save(new City("Paris", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/La_Tour_Eiffel_vue_de_la_Tour_Saint-Jacques%2C_Paris_ao%C3%BBt_2014_%282%29.jpg/280px-La_Tour_Eiffel_vue_de_la_Tour_Saint-Jacques%2C_Paris_ao%C3%BBt_2014_%282%29.jpg"));
            cityRepository.save(new City("Marseille", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Notre_Dame_de_la_Garde.jpg/280px-Notre_Dame_de_la_Garde.jpg"));
            cityRepository.save(new City("Lyon", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/From_Croix_Rousse_To_Fourvi%C3%A8re_%28161423721%29.jpeg/1920px-From_Croix_Rousse_To_Fourvi%C3%A8re_%28161423721%29.jpeg"));
            cityRepository.save(new City("Toulouse", "https://t4.ftcdn.net/jpg/05/10/54/19/360_F_510541978_JdZbQd0YPXhvR8BKaoqdU5zm9cl0Icke.jpg"));
            cityRepository.save(new City("Nice", "https://img.static-kl.com/images/media/763BE99A-E6F1-49F2-B8BD022016F42ADF"));
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
