package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.repository.HistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Service
public class HistorialServiceImpl implements HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

}
