package com.josuenoj.futecaManager.utils;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

//Configuración para BCrypt | Librería para encriptación de datos.
@Component
public class BCryptSecurity {

    //Método para encriptar la password
    public String encodePassword(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    //Método para verificar si coincide el texto plano con el hash (password encriptada)
    public boolean checkPassword(String password, String hashedPassword){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }

}
