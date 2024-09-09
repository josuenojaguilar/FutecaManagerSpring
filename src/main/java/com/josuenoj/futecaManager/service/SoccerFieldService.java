package com.josuenoj.futecaManager.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josuenoj.futecaManager.model.SoccerField;
import com.josuenoj.futecaManager.repository.SoccerFieldRepository;
import com.josuenoj.futecaManager.service.IService.ISoccerFieldService;

@Service
public class SoccerFieldService implements ISoccerFieldService {
    @Autowired
    private SoccerFieldRepository soccerFieldRepository;

    @Override
    public List<SoccerField> listFields() {
        return soccerFieldRepository.findAll();
    }

    @Override
    public SoccerField findFieldById(Long id) {
        return soccerFieldRepository.findById(id).orElse(null);
    }

    @Override
    public SoccerField save (SoccerField soccerField){
        return soccerFieldRepository.save(soccerField);
    }
}
