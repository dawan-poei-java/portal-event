package fr.dawan.portal_event.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.CityDto;
import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.dto.TypeEventDto;
import fr.dawan.portal_event.entities.Event;
import fr.dawan.portal_event.repositories.CityRepository;
import fr.dawan.portal_event.repositories.EventRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class EventService implements IEventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;


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
    public EventDto saveOrUpdate(EventDto eventDto) {
        Event event = DtoTool.convert(eventDto, Event.class);
        Event savedEvent = eventRepository.saveAndFlush(event);
        return DtoTool.convert(savedEvent, EventDto.class);
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

}
