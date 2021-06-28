/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serlew.app.models.service;

import com.serlew.app.models.entity.Heroe;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author "Lewczuk, Sergio Daniel"
 */
public interface IHeroeService {
    
    public List<Heroe> listarHeroes();
    
    public Page<Heroe> listarHeroesPaginable(Pageable pageable);
    
    public void guardarHeroe(Heroe heroe);
    
    public void eliminarHeroe(Heroe heroe);
    
    public Heroe encontrarHeroePorId(Heroe heroe);
    
}
