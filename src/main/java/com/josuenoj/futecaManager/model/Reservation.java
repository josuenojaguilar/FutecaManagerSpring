package com.josuenoj.futecaManager.model;

import java.sql.Date;

import com.josuenoj.futecaManager.utils.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @FutureOrPresent
    private Date start; //UTC -> GMT-6
    @NotBlank
    @FutureOrPresent
    private Date end;
    private String payment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotBlank
    @ManyToOne //Por defecto tiene un Eager (población de datos)
    private User user;
    @NotBlank
    @ManyToOne
    private SoccerField soccerField;
}
