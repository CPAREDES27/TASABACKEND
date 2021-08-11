package com.incloud.hcp.rest;


import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroImports;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/Maestros")
public class MaestrosRest {

    @Autowired
    private JCOMaestrosService MaestroService;

    @PostMapping(value = "/ConsultarMaestro/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ConsultarMaestro(@RequestBody MaestroImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerMaestro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }



}
