package com.josuenoj.futecaManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josuenoj.futecaManager.model.Reservation;
import com.josuenoj.futecaManager.model.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //Metodo personalizado que busque y devuelva todas las reservas de un solo usuario
    List<Reservation> findByUser(User user);
}
