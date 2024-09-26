package fr.dawan.portal_event.services;

import java.util.List;

import fr.dawan.portal_event.dto.ReservationDto;

public interface IReservationService {
    List<ReservationDto> getAll();
    ReservationDto getById(long id);
    ReservationDto saveOrUpdate(ReservationDto dto);
    void deleteById(long id);
}
