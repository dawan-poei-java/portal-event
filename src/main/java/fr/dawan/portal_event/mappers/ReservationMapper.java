package fr.dawan.portal_event.mappers;

import fr.dawan.portal_event.dto.ReservationDto;
import fr.dawan.portal_event.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EventMapper.class, UserMapper.class, PricingMapper.class})
public interface ReservationMapper {
    @Mapping(target = "event", source = "event")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "pricing", source = "pricing")
    ReservationDto toDto(Reservation reservation);

    @Mapping(target = "event", source = "event")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "pricing", source = "pricing")
    Reservation toEntity(ReservationDto reservationDto);
}
