package com.josuenoj.futecaManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josuenoj.futecaManager.model.User;
import com.josuenoj.futecaManager.repository.UserRepository;
import com.josuenoj.futecaManager.service.IService.IUserService;
import com.josuenoj.futecaManager.utils.BCryptSecurity;

@Service
//Lógica de negocio
public class UserService implements IUserService {

    //Inyección de dependencia (Repositorio)
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptSecurity bCryptSecurity;

    @Override
    public List<User> listUsers(){
        return userRepository.findAll();
    }
    
    @Override
    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    //Método con la lógica para registrar un usuario
    @Override
    public User register(User user){
        //Más lógica
        if(user.getPassword() != null){
            user.setPassword(bCryptSecurity.encodePassword(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public boolean login(String username, String password){
        //Más lógica
        //Validar que exista el usuario con ese username
        //Validar que ese usuario tenga la contraseña que envió el usuario.
        return false;
    }
}
