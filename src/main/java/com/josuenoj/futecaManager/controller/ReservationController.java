package com.josuenoj.futecaManager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.josuenoj.futecaManager.DTO.ReservationResponseDTO;
import com.josuenoj.futecaManager.DTO.ReservationSaveDTO;
import com.josuenoj.futecaManager.model.Reservation;
import com.josuenoj.futecaManager.service.ReservationService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

 
@RestController
@RequestMapping("/futecaManager/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
 
 
    @PostMapping()
    public ResponseEntity<?> saveReservation(
        @Valid @RequestBody ReservationSaveDTO reservationDTO,
        BindingResult result
    ) {
        Map<String, Object> res = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
                res.put("Errors", errors);
                return ResponseEntity.badRequest().body(res);
        }
        try {
            Reservation reservation = reservationService.save(reservationDTO);
            res.put("message", "Reservación guardada exitosamente");
            res.put("reservation", reservation);
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar la reservacion, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> myReservations(@PathVariable Long userId) {
        Map<String, Object> res = new HashMap<>();
        try{
            List<ReservationResponseDTO> reservations = reservationService.myReservations(userId);
            if(reservations == null || reservations.isEmpty()){
                res.put("message", "Aún no tienes reservaciones creadas");
                return ResponseEntity.status(404).body(res);
            }else{
                return ResponseEntity.ok(reservations);
            }
        }catch(Exception err){
            res.put("message", "Error general al obtener los datos");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }
    
}
