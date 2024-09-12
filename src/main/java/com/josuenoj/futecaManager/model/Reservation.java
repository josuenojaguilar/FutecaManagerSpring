package com.josuenoj.futecaManager.model;

import java.sql.Timestamp;

import com.josuenoj.futecaManager.utils.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Crear un constructor vacío
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull //@NotBlank es solo para STRINGS!!!
    @FutureOrPresent
    private Timestamp start; //UTC -> GMT-6
    @NotNull
    @FutureOrPresent
    private Timestamp end;
    private String payment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    @ManyToOne //Por defecto tiene un Eager (población de datos)
    private User user;
    @NotNull
    @ManyToOne
    private SoccerField soccerField;
}
