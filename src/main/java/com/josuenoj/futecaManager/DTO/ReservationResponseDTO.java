package com.josuenoj.futecaManager.DTO;

import java.sql.Timestamp;

import com.josuenoj.futecaManager.model.SoccerField;
import com.josuenoj.futecaManager.utils.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {
    private Long id;
    private Timestamp start;
    private Timestamp end;
    private String payment;
    private Status status;
    private UserClearDTO user;
    private SoccerField soccerFieldId;
}
