package com.josuenoj.futecaManager.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SoccerFieldSaveDTO {
    @NotBlank(message = "El nombre de la cancha es obligatorio")
    private String name;
    @NotBlank(message = "El tipo de cancha es obligatoria")
    private String type;
    private String capacity;
    
}
