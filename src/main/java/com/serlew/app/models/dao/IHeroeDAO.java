package com.serlew.app.models.dao;

import com.serlew.app.models.entity.Heroe;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author "Lewczuk, Sergio Daniel"
 */
public interface IHeroeDAO extends PagingAndSortingRepository<Heroe, Long> {

        

}
