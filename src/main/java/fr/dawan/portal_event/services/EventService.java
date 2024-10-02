package fr.dawan.portal_event.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.portal_event.dto.CityDto;
import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.dto.TypeEventDto;
import fr.dawan.portal_event.dto.EventDto.PricingDto;
import fr.dawan.portal_event.dto.EventDto.UserDto;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.entities.Pricing;
import fr.dawan.portal_event.entities.TypeEvent;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.repositories.CityRepository;
import fr.dawan.portal_event.repositories.EventRepository;
import fr.dawan.portal_event.repositories.PricingRepository;
import fr.dawan.portal_event.repositories.TypeEventRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class EventService implements IEventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TypeEventRepository typeEventRepository;

    @Autowired
    private PricingRepository pricingRepository;


    public List<EventDto> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();
        for(Event event : events){
            eventDtos.add(DtoTool.convert(event, EventDto.class));
        }
        return eventDtos;
    }

    public EventDto getById(long id){
        return DtoTool.convert(eventRepository.findById(id).get(), EventDto.class);
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    @Override
    @Transactional
    public EventDto saveOrUpdate(EventDto eventDto) {
        System.out.println("Début de saveOrUpdate avec eventDto: " + eventDto);

        Event event = DtoTool.convert(eventDto, Event.class);
        System.out.println("Event après conversion: " + event);

        // Vérifications et initialisations
        if (event.getImages() == null) {
            event.setImages(new ArrayList<>());
        }

        // Vérification de la ville
        if (event.getCity() == null) {
            throw new RuntimeException("La ville ne peut pas être nulle");
        }
        System.out.println("Ville de l'événement: " + event.getCity());

        // Vérification du type d'événement
        if (event.getTypeEvent() == null) {
            throw new RuntimeException("Le type d'événement ne peut pas être nul");
        }
        System.out.println("Type d'événement: " + event.getTypeEvent());

        // Vérification de l'adresse
        if (event.getAddress() == null || event.getAddress().trim().isEmpty()) {
            throw new RuntimeException("L'adresse est obligatoire");
        }


        
        // Vérification de l'ID de l'organisateur
        if (event.getOrganizer() == null || event.getOrganizer().getId() == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long userId = null;
            if (auth != null && auth.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) auth.getPrincipal();
                userId = jwt.getClaim("user_id");
            }
            if (userId == null) {
                throw new RuntimeException("Impossible de récupérer l'ID de l'utilisateur à partir du JWT");
            } else {
                if (event.getOrganizer() == null) {
                    event.setOrganizer(new User()); // Assurez-vous d'importer la classe User
                }
                event.getOrganizer().setId(userId);
            }
            if(event.getOrganizer() == null) throw new RuntimeException("L'ID de l'organisateur est obligatoire");
        }
        System.out.println("ID de l'organisateur : " + event.getOrganizer().getId());

        try {
            System.out.println("Tentative de sauvegarde de l'événement");
            System.out.println("Sauvegarde - typeEvent : " + event.getTypeEvent());
            
            // Vérifier si c'est une création ou une mise à jour
            boolean isNewEvent = (event.getId() == null || event.getId() == 0);
            
            if (isNewEvent) {
                // Sauvegarde de l'événement sans les pricings seulement pour un nouvel événement
                event.setPricings(null);
            }
            
            Event savedEvent = eventRepository.save(event);
            System.out.println("Événement sauvegardé avec succès. ID: " + savedEvent.getId());

            if (isNewEvent && savedEvent.getPricings() == null) {
                // Gestion des tarifs pour un nouvel événement
                if (eventDto.getPricings() != null && !eventDto.getPricings().isEmpty()) {
                    System.out.println("Traitement des tarifs");
                    List<Pricing> pricings = new ArrayList<>();
                    for (PricingDto pricingDto : eventDto.getPricings()) {
                        Pricing pricing = new Pricing();
                        pricing.setName(pricingDto.getName());
                        pricing.setPrice(pricingDto.getPrice());
                        pricing.setEvent(savedEvent);
                        pricings.add(pricing);
                    }
                    pricingRepository.saveAll(pricings);
                    System.out.println("Pricings sauvegardés avec succès");
                }
    
                // Rechargement de l'événement pour obtenir les pricings associés
                savedEvent = eventRepository.findById(savedEvent.getId()).orElseThrow(() -> new RuntimeException("Événement non trouvé après sauvegarde"));
            }

            EventDto resultDto = DtoTool.convert(savedEvent, EventDto.class);
            System.out.println("Conversion en DTO réussie. Retour du résultat");
            return resultDto;
        } catch (Exception e) {
            System.err.println("Erreur lors de la sauvegarde de l'événement: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la sauvegarde de l'événement: " + e.getMessage());
        }
    }

    public List<EventDto> getUpcomingEvents() {
        List<Event> upcomingEvents = eventRepository.findUpcomingEvents();
        List<EventDto> upcomingEventsDto = new ArrayList<>();
        for(Event event : upcomingEvents){
            upcomingEventsDto.add(DtoTool.convert(event, EventDto.class));
        }
        return upcomingEventsDto;
    }

    public List<EventDto> getEventsByCity(String cityName) {
        long cityId = cityRepository.findByName(cityName).getId();
        List<Event> eventsByCity = eventRepository.findAllByCityId(cityId);
        List<EventDto> eventsDtos = new ArrayList<>();
        for(Event event: eventsByCity){
            eventsDtos.add(DtoTool.convert(event, EventDto.class));
        }
        return eventsDtos;
    }


    public EventDto getPopularEvent(){
        return DtoTool.convert(eventRepository.findMostPopularEvent(), EventDto.class);
    }

    public EventDto getEventByIdAndCity(long id, String cityName) {
        long cityId = cityRepository.findByName(cityName).getId();
        EventDto eventDto = DtoTool.convert(eventRepository.findEventByIdAndCityName(id ,cityId), EventDto.class);
        return eventDto;
    }

    public List<EventDto> getEventsByOrganizer(long organizerId) {
        // TODO: vérifier si l'organisateur existe vraiment
        List<Event> events = eventRepository.findEventsByOrganizerId(organizerId);
        List<EventDto> dtos = new ArrayList<>();
        for(Event event: events){
            dtos.add(DtoTool.convert(event, EventDto.class));
        }
        return dtos;
    }


}
