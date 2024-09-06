package com.josuenoj.futecaManager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.josuenoj.futecaManager.DTO.UserRegisterDTO;
import com.josuenoj.futecaManager.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController //@Controller @ResponseBody
@RequestMapping("/futecaManager/v1/auth") //Ruta general
public class UserController {

    //Inyectar la dependencia del servicio
    @Autowired
    UserService userService;

    //Rutas específicas
    /**
     * Método para listar usuarios
     * @return ResponseEntity con las diferentes respuestas de error y la respuesta correcta
     */
    @GetMapping()
    public ResponseEntity<?> getMethodName() {
        Map<String, Object> res = new HashMap<>();
        try { 
            return ResponseEntity.ok().body(userService.listUsers());
            //Error de conexión a la BD
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
            //Error de consulta a la BD
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
            //Error general o genérico
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    //Data transfer object / patron de diseño que sirve para transoportar datos entre diferenetes capas.

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestPart("profilePicture") MultipartFile profilePicture,
        @RequestBody UserRegisterDTO user
    ) {
        Map<String, Object> res = new HashMap<>();
        try {
            System.out.println(user);
            res.put("message", "Usuario recibido correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar el usuario, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
    
    
}
