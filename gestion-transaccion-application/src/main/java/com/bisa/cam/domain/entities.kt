package com.bisa.cam.domain

import java.sql.Timestamp


data class Transaccion(
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

data class ResultadoTransaccion(
    var resultado : Boolean?=null,
    var descripcion : String?=null,
    var importe : Double?=null,
    var moneda : String?=null
)

data class Cuenta(
    var id: Long?=null,
    var nroCuenta: Long?=null,
    var tipoCuenta: String?=null,
    var moneda: String?=null,
    var saldo: Double?=null,
    var idCliente: Long?=null,
    var fechaApertura: Timestamp?=null,
    var lugarApertura: String?=null,
    var estado: String?=null

)
