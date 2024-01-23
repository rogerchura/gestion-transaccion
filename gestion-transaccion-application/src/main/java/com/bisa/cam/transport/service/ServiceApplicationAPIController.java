package com.bisa.cam.transport.service;


import com.bisa.cam.business.interactors.TransaccionApplicationInteractor;
import com.bisa.cam.transport.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Implementacion de la API
 *
 */
@RestController
public class ServiceApplicationAPIController implements ServiceApplicationAPI {
    @Autowired
    private TransaccionApplicationInteractor applicationInteractor;

    private final Logger logger = LogManager.getLogger(getClass());


    @Override
    public ResponseEntity procesarDeposito(DepositoRequestDto requestdto) {
        DepositoResponseDto responseDto = applicationInteractor.realizarDeposito(requestdto);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity procesarRetiro(RetiroRequestDto requestdto) {
        RetiroResponseDto responseDto = applicationInteractor.realizarRetiro(requestdto);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity procesarConversion(ConversionRequestDto requestdto) {

        ConversionResponseDto responseDto = new ConversionResponseDto();
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity consultaTransacciones(String nroCuenta) {
        Long numcuenta = Long.valueOf(Optional.ofNullable(nroCuenta).orElse("0"));
        ConsultaTransaccionResponseDto responseDto = applicationInteractor.consultaTransacciones(numcuenta);
        return ResponseEntity.ok(responseDto);
    }

}