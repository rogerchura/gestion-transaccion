package com.bisa.cam.business.interactors;


import com.bisa.cam.business.repositories.TransaccionRepository;
import com.bisa.cam.domain.Cuenta;
import com.bisa.cam.domain.ResultadoTransaccion;
import com.bisa.cam.domain.Transaccion;
import com.bisa.cam.transport.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransaccionApplicationInteractor {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private TransaccionRepository repository;

    public ConsultaTransaccionResponseDto consultaTransacciones(Long nrocuenta) {

        ConsultaTransaccionResponseDto responseDto = new ConsultaTransaccionResponseDto();;
        List<Transaccion> listaTransaccion = null;
        try {
            listaTransaccion = repository.consultaTransacciones(nrocuenta);
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
        }

        if(listaTransaccion!=null && listaTransaccion.size()>0){
            responseDto.setError(new ErrorResponseDto());
            List<TransaccionDto> lista = new ArrayList<>();
            TransaccionDto trdto;
            for(Transaccion trx: listaTransaccion){
                trdto = new TransaccionDto();
                trdto.setIdTrx(trx.getIdTrx());
                trdto.setEstado(trx.getEstado());
                trdto.setCambio(trx.getCambio());
                trdto.setTipoCambio(trx.getTipoCambio());
                trdto.setError(trx.getError());
                trdto.setCodAtm(trx.getCodAtm());
                trdto.setNroCuenta(trx.getNroCuenta());
                trdto.setOperacion(trx.getOperacion());
                trdto.setFecha(trx.getFecha());
                trdto.setIdCliente(trx.getIdCliente());
                trdto.setMoneda(trx.getMoneda());
                trdto.setImporte(trx.getImporte());
                lista.add(trdto);
            }
            responseDto.setLista(lista);
        }else{
            responseDto.setError(new ErrorResponseDto(240l, "No se encontraron transacciones."));
            responseDto.setLista(Collections.emptyList());
        }
        return responseDto;
    }

    public RetiroResponseDto realizarRetiro(RetiroRequestDto request) {
        Transaccion transac = null;
        RetiroResponseDto responseDto = new RetiroResponseDto();
        try {
            if(request!=null){
                transac = new Transaccion();
                transac.setNroCuenta(request.getNroCuenta());
                transac.setImporte(request.getImporte());
                transac.setMoneda(request.getMoneda());
                transac.setCodAtm(request.getCodAtm());
                ResultadoTransaccion resultado = repository.realizarRetiro(transac);

                if(resultado.getResultado()){
                    responseDto.setError(new ErrorResponseDto(0l, resultado.getDescripcion()));
                    responseDto.setEstado("PROCESADO");
                }else{
                    responseDto.setError(new ErrorResponseDto(240l, resultado.getDescripcion()));
                    responseDto.setEstado("ERRADO");
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            responseDto.setError(new ErrorResponseDto(250l, "Error al procesar el retiro."+e.getMessage()));
            responseDto.setEstado("ERRADO");
        }
        return responseDto;
    }

    public DepositoResponseDto realizarDeposito(DepositoRequestDto request) {
        Transaccion transac = null;
        DepositoResponseDto responseDto = new DepositoResponseDto();
        try {
            if(request!=null){
                transac = new Transaccion();
                transac.setNroCuenta(request.getNroCuenta());
                transac.setImporte(request.getImporte());
                transac.setMoneda(request.getMoneda());
                transac.setCodAtm(request.getCodAtm());
                ResultadoTransaccion resultado = repository.realizarDeposito(transac);

                if(resultado.getResultado()){
                    responseDto.setError(new ErrorResponseDto(0l, resultado.getDescripcion()));
                    responseDto.setEstado("PROCESADO");
                }else{
                    responseDto.setError(new ErrorResponseDto(240l, resultado.getDescripcion()));
                    responseDto.setEstado("ERRADO");
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            responseDto.setError(new ErrorResponseDto(250l, "Error al procesar el retiro."+e.getMessage()));
            responseDto.setEstado("ERRADO");
        }
        return responseDto;
    }

}
