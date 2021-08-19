package com.incloud.hcp.rest;

import com.incloud.hcp.util.Mail.CorreoDto;
import com.incloud.hcp.util.Mail.CorreoService;
import com.incloud.hcp.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/correo")
public class CorreoRest {

    @Autowired
    private CorreoService correoService;

    @PostMapping(value = "/EnviarCorreo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> Enviar(@RequestBody CorreoDto imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.correoService.EnviarCorreo(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
    @PostMapping(value = "/EnviarConAdjunto", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> EnviarConAdjunto(@RequestBody CorreoDto imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.correoService.EnviarConAdjunto(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
