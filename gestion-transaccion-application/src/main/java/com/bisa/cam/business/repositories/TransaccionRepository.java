package com.bisa.cam.business.repositories;

import com.bisa.cam.domain.ResultadoTransaccion;
import com.bisa.cam.domain.Transaccion;

import java.util.List;


public interface TransaccionRepository {

    List<Transaccion> consultaTransacciones(Long numCuenta) throws Exception;

    ResultadoTransaccion realizarRetiro(Transaccion transaccion) throws Exception;

    ResultadoTransaccion realizarDeposito(Transaccion transaccion) throws Exception;

    ResultadoTransaccion realizarConversion(Transaccion transaccionOrigen, String monedaDestino) throws Exception;

}
