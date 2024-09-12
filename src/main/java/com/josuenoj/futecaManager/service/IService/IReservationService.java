package com.josuenoj.futecaManager.service.IService;

import java.util.List;

import com.josuenoj.futecaManager.DTO.ReservationResponseDTO;
import com.josuenoj.futecaManager.DTO.ReservationSaveDTO;
import com.josuenoj.futecaManager.model.Reservation;

public interface IReservationService {
    //Método para listar reservaciones (SOLO DE UN USUARIO EN ESPECÍFICO)
    List<ReservationResponseDTO> myReservations(Long userId);

    //Método para mostrar solo 1 reservación por su Id
    Reservation findByIdReservation(Long id);

    //Método para guardar una reservación
    Reservation save(ReservationSaveDTO reservationDTO);

}
