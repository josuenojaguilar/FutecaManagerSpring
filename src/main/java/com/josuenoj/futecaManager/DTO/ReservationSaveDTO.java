package com.josuenoj.futecaManager.DTO;

import java.time.LocalDateTime;

import com.josuenoj.futecaManager.utils.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationSaveDTO {
    @NotNull(message = "La fecha de inicio no puede ir vacía")
    @FutureOrPresent
    private LocalDateTime start; 
    @NotNull(message = "La fecha de finalización no puede ir vacía")
    @FutureOrPresent
    private LocalDateTime end;
    private String payment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull(message = "No hay un usuario para reservar")
    private Long userId;
    @NotNull(message = "No se seleccionó una cancha a reservar")
    private Long soccerFieldId;
}
