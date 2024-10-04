package fr.dawan.portal_event.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.ReservationDto;
import fr.dawan.portal_event.entities.Reservation;
import fr.dawan.portal_event.repositories.ReservationRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<ReservationDto> getAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDto> result = new ArrayList<ReservationDto>();

        for(Reservation reservation: reservations){
            ReservationDto dto = DtoTool.convert(reservation, ReservationDto.class);
            result.add(dto);
        }
        return result;
    }
    @Override
    public List<ReservationDto> getReservationsByUserId(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);

        List<ReservationDto> result = new ArrayList<>();
        for(Reservation reservation: reservations){
            ReservationDto dto = DtoTool.convert(reservation, ReservationDto.class);
            result.add(dto);
        }
        return result;
    }

    @Override
    public ReservationDto getById(long id) {
        return DtoTool.convert(reservationRepository.findById(id).get(), ReservationDto.class);
    }

    @Override
    public ReservationDto saveOrUpdate(ReservationDto dto) {
        Reservation reservation = DtoTool.convert(dto, Reservation.class);
        return DtoTool.convert(reservationRepository.saveAndFlush(reservation), ReservationDto.class);
    }

    @Override
    public void deleteById(long id) {
        reservationRepository.deleteById(id);
    }


}
