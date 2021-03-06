package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.dto.TripuEmbarcaImports;
import com.incloud.hcp.jco.maestro.service.JCOTripEmbarcService;
import com.incloud.hcp.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/tripulantesembarcacion")
public class TripulantesEmbarcacionRest {

    @Autowired
    private JCOTripEmbarcService jcoTripEmbarcService;

    @PostMapping(value = "/Editar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> Editar(@RequestBody TripuEmbarcaImports imports){

        try {
            return Optional.ofNullable(this.jcoTripEmbarcService.EditarTripuEmbarca(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
}
