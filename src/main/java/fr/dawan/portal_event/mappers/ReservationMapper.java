package fr.dawan.portal_event.mappers;

import fr.dawan.portal_event.dto.ReservationDto;
import fr.dawan.portal_event.entities.Reservation;
import org.mapstruct.Mapper;

@Mapper
public interface ReservationMapper {
    ReservationDto toDto(Reservation reservation);
    Reservation toEntity(ReservationDto reservationDto);

}
