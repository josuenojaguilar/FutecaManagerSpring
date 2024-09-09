package com.josuenoj.futecaManager.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.josuenoj.futecaManager.DTO.SoccerFieldSaveDTO;
import com.josuenoj.futecaManager.model.SoccerField;
import com.josuenoj.futecaManager.service.CloudinaryService;
import com.josuenoj.futecaManager.service.SoccerFieldService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/futecaManager/v1/soccerField")
public class SoccerFieldController {
    private final Logger logger = LoggerFactory.getLogger(SoccerFieldController.class);

    @Autowired
    SoccerFieldService soccerFieldService;

    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping()
    public ResponseEntity<?> listFields(){
        Map<String, Object> res = new HashMap<>();
        logger.debug("Iniciando la conexión a la base de datos");
        try {
            logger.debug("Iniciando consulta a la base de datos");
            List<SoccerField> fieldsList = soccerFieldService.listFields();
            if(fieldsList == null || fieldsList.isEmpty()){
                logger.warn("No existen canchas registradas");
                res.put("Mensaje","No existen canchas registradas");
                return ResponseEntity.status(404).body(res);
            }else {
                logger.info("Obteniendo la lista de canchas");
                return ResponseEntity.ok(fieldsList);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            res.put("Mensaje","Error al momento de conectarse a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información en la base de datos");
            res.put("Mensaje","Error al momento de consultar la información en la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(
            @RequestPart("photo") MultipartFile photo,
            @Valid @ModelAttribute SoccerFieldSaveDTO soccerFieldDTO, 
            BindingResult result){
        Map<String,Object> res = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            res.put("Errores",errors);
            return ResponseEntity.badRequest().body(res);
        }
        try {
            Map<String, Object> uploadResult = cloudinaryService.uploadProfilePicture(photo, "soccerFields");
            String img = uploadResult.get("url").toString();
            Long id = null;
            SoccerField soccerField = new SoccerField(
                id,
                soccerFieldDTO.getName(),
                soccerFieldDTO.getType(),
                soccerFieldDTO.getCapacity(),
                img
            );
            soccerFieldService.save(soccerField);

            res.put("Mensaje", "Cancha agregada exitosamente");
            res.put("Usuario", soccerField);
            return ResponseEntity.ok(res);

        }catch (IOException e) {
            res.put("Mensaje", "Error al subir la imagen");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            res.put("Mensaje","Error al momento de conectarse a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información en la base de datos");
            res.put("Mensaje","Error al momento de consultar la información en la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);

        }
    }

}
