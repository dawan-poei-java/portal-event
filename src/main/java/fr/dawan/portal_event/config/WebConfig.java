package fr.dawan.portal_event.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.utils.CityDtoConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    private final CityDtoConverter cityDtoConverter;

    public WebConfig(CityDtoConverter cityDtoConverter) {
        this.cityDtoConverter = cityDtoConverter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:5173") /*TODO: changer en prod ou en fonction du port du projet React */
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .allowCredentials(true);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(cityDtoConverter);
        registry.addConverter(String.class, City.class, id -> new City(Long.valueOf(id)));
    }
}
