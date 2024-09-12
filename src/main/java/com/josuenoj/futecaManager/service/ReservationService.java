package com.josuenoj.futecaManager.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josuenoj.futecaManager.DTO.ReservationResponseDTO;
import com.josuenoj.futecaManager.DTO.ReservationSaveDTO;
import com.josuenoj.futecaManager.DTO.UserClearDTO;
import com.josuenoj.futecaManager.model.Reservation;
import com.josuenoj.futecaManager.model.SoccerField;
import com.josuenoj.futecaManager.model.User;
import com.josuenoj.futecaManager.repository.ReservationRepository;
import com.josuenoj.futecaManager.service.IService.IReservationService;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    @Autowired
    SoccerFieldService soccerFieldService;

    @Override
    public List<ReservationResponseDTO> myReservations(Long userId) {
        User user = userService.getUser(userId);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations
            .stream()
            .map(reservation -> responseDTO(reservation))
            .collect(Collectors.toList());
    }

    @Override
    public Reservation findByIdReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation save(ReservationSaveDTO reservationDTO) {
        try {
            //Convertir la fecha que llega en STRING (LocalDateTime) a TIMESTAMP
            Timestamp startDate = Timestamp.valueOf(reservationDTO.getStart());
            //Convertir la fecha que llega en STRING (LocalDateTime) a TIMESTAMP
            Timestamp endDate = Timestamp.valueOf(reservationDTO.getEnd());
            //Obtenemos el usuario completo que viene en la solicitud (userId)
            User user = userService.getUser(reservationDTO.getUserId());
            //Obtenemos la cancha completa que viene en la solicitud (soccerFieldId)
            SoccerField soccerField = soccerFieldService.findFieldById(reservationDTO.getSoccerFieldId());
            Reservation reservation = new Reservation(
                null,
                startDate,
                endDate,
                reservationDTO.getPayment(),
                reservationDTO.getStatus(),
                user,
                soccerField
            );

            return reservationRepository.save(reservation);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
    }

    private ReservationResponseDTO responseDTO(Reservation reservation){
         User user = reservation.getUser();
        UserClearDTO userDTO = new UserClearDTO(
            user.getName(),
            user.getSurname(),
            user.getSurname()
        );

        ReservationResponseDTO dto = new ReservationResponseDTO(
            reservation.getId(),
            reservation.getStart(),
            reservation.getEnd(),
            reservation.getPayment(),
            reservation.getStatus(),
            userDTO,
            reservation.getSoccerField()
        );

        return dto;
    }
    
}
