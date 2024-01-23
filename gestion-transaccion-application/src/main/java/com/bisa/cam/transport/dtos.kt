package com.bisa.cam.transport

import com.fasterxml.jackson.annotation.JsonRootName
import java.sql.Timestamp


@JsonRootName("DepositoRequest")
open class DepositoRequestDto(
    var nroCuenta: Long?=null,
    var moneda: String?=null,
    var importe: Double?=null,
    var codAtm: String?=null
)

@JsonRootName("DepositoResponse")
open class DepositoResponseDto(
    var error : ErrorResponseDto?=null,
    var estado: String?=null
)

@JsonRootName("RetiroRequest")
open class RetiroRequestDto(
    var nroCuenta: Long?=null,
    var moneda: String?=null,
    var importe: Double?=null,
    var codAtm: String?=null
)

@JsonRootName("RetiroResponse")
open class RetiroResponseDto(
    var error : ErrorResponseDto?=null,
    var estado: String?=null
)

@JsonRootName("ConversionRequest")
open class ConversionRequestDto(
    var nroCuenta: Long?=null,
    var monedaOrigen: String?=null,
    var importe: Double?=null,
    var monedaDestino: String?=null,
    var codAtm: String?=null
)

@JsonRootName("ConversionResponse")
open class ConversionResponseDto(
    var error : ErrorResponseDto?=null,
    var nuevoImporte: Double?=null,
    var nuevaMoneda: String?=null
)

@JsonRootName("ConsultaTransaccionRequest")
open class ConsultaTransaccionRequestDto(
    var nroCuenta: Long
)

@JsonRootName("ConsultaTransaccionResponse")
open class ConsultaTransaccionResponseDto(
    var error : ErrorResponseDto?=null,
    var lista: List<TransaccionDto>?=null
)

open class TransaccionDto(
    var idTrx: Long?=null,
    var fecha: Timestamp?=null,
    var idCliente: Long?=null,
    var nroCuenta: Long?=null,
    var operacion: String?=null,
    var importe: Double?=null,
    var moneda: String?=null,
    var cambio: Double?=null,
    var tipoCambio: String?=null,
    var estado: String?=null,
    var error: String?=null,
    var codAtm: String?=null
)


/*********************************** */
@JsonRootName("CrearCuentaResponse")
open class CrearCuentaResponseDto(
    var error : ErrorResponseDto?=null,
    var nroCuenta: Long?=null
)

@JsonRootName("CrearCuentaRequest")
open  class CrearCuentaRequestDto(
    var nroCuenta: Long,
    var tipoCuenta: String,
    var moneda: String,
    var idCliente: Long
)

@JsonRootName("SaldoResponse")
open class SaldoResponseDto(
    var error : ErrorResponseDto?=null,
    var saldoCuenta: ConsultaSaldoResponseDto?=null
)

@JsonRootName("ErrorResponse")
open class ErrorResponseDto(
    var codError: Long?=0,
    var descError: String?="Sin errores"
)

@JsonRootName("ConsultaSaldoResponse")
open class ConsultaSaldoResponseDto(
    var nroCuenta: Long?=null,
    var tipoCuenta: String?=null,
    var moneda: String?=null,
    var saldo: Double?=null,
    var idCliente: Long?=null,
    var fechaApertura: Timestamp?=null,
    var lugarApertura: String?=null,
    var estado: String?=null
)




