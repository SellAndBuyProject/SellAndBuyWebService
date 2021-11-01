package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.service.HistorialService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@RestController
@Tag(name = "Historial", description = "Gestión del historial")
public class HistorialController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private HistorialService historialService;
}
