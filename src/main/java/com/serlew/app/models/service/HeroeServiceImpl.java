/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serlew.app.models.service;

import com.serlew.app.models.dao.IHeroeDAO;
import com.serlew.app.models.entity.Heroe;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author "Lewczuk, Sergio Daniel"
 */
@Service
public class HeroeServiceImpl implements IHeroeService {

    @Autowired
    private IHeroeDAO heroeDAO;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Heroe> listarHeroes() {
        return (List<Heroe>) heroeDAO.findAll();
    }

    @Override
    @Transactional
    public void guardarHeroe(Heroe heroe) {
        heroeDAO.save(heroe);
    }

    @Override
    @Transactional
    public void eliminarHeroe(Heroe heroe) {
        heroeDAO.delete(heroe);
    }

    @Override
    @Transactional(readOnly = true)
    public Heroe encontrarHeroePorId(Heroe heroe) {
        return heroeDAO.findById(heroe.getIdHeroe()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Heroe> listarHeroesPaginable(Pageable pageable) {
        return heroeDAO.findAll(pageable);
    }

    
}
