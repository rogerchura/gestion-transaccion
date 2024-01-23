package com.bisa.cam.transport.service;

import com.bisa.cam.transport.*;
import com.bisa.cam.utils.spring.rest.RestControllerStub;
import com.bisa.cam.utils.spring.rest.validators.request.PayloadCheck;
import com.bisa.cam.utils.spring.rest.validators.request.RequestValidation;
import com.bisa.cam.utils.spring.rest.validators.request.json.JsonSchema;
import com.bisa.cam.utils.spring.rest.validators.request.params.PathVariableCheck;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>API Specification</p>
 *  Gestion Transacciones
 *
 */
@RequestMapping("/gestion-transaccion")
@OpenAPIDefinition(info = @Info(title = "API de Gestion de Transacciones",
        description = "La API permite realizar las siguientes operaciones: Deposito, Retiro, Conversiones de moneda.", version = "1.0.0",
        contact = @Contact(name = "Cajero Automatico Multi-Moneda", url = "http://www.cam.com"))
)
public interface ServiceApplicationAPI extends RestControllerStub {

    @PostMapping("/realizar-deposito")
    @Operation(tags = {"Gestion Transaccion"}, responses =
            {
                    @ApiResponse(responseCode = "200", description = "Solicitud aceptada",
                            content = @Content(schema = @Schema(implementation = DepositoResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "El cuerpo de la solicitud es invalido",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "422", description = "No se pudo procesar la solicitud",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno en el servicio",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @RequestValidation(payload = @PayloadCheck(
            jsonSchema = @JsonSchema(""))
    )
    /*@RequestValidation(payload = @PayloadCheck(
            jsonSchema = @JsonSchema("classpath:/schemas/json/crear-cuenta-request.json")))*/
    ResponseEntity procesarDeposito(@RequestBody DepositoRequestDto requestdto);


    @PostMapping("/realizar-retiro")
    @Operation(tags = {"Gestion Transaccion"}, responses =
            {
                    @ApiResponse(responseCode = "200", description = "Solicitud aceptada",
                            content = @Content(schema = @Schema(implementation = RetiroResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "El cuerpo de la solicitud es invalido",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "422", description = "No se pudo procesar la solicitud",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno en el servicio",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @RequestValidation(payload = @PayloadCheck(
            jsonSchema = @JsonSchema(""))
    )
    /*@RequestValidation(payload = @PayloadCheck(
            jsonSchema = @JsonSchema("classpath:/schemas/json/crear-cuenta-request.json")))*/
    ResponseEntity procesarRetiro(@RequestBody RetiroRequestDto requestdto);


    @PostMapping("/realizar-conversion")
    @Operation(tags = {"Gestion Transaccion"}, responses =
            {
                    @ApiResponse(responseCode = "200", description = "Solicitud aceptada",
                            content = @Content(schema = @Schema(implementation = ConversionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "El cuerpo de la solicitud es invalido",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "422", description = "No se pudo procesar la solicitud",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno en el servicio",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @RequestValidation(payload = @PayloadCheck(
            jsonSchema = @JsonSchema(""))
    )
    /*@RequestValidation(payload = @PayloadCheck(
            jsonSchema = @JsonSchema("classpath:/schemas/json/crear-cuenta-request.json")))*/
    ResponseEntity procesarConversion(@RequestBody ConversionRequestDto requestdto);

    @GetMapping("/consulta/{nro-cuenta}")
    @Operation(tags = {"Gestion Transaccion"}, responses =
            {
                    @ApiResponse(responseCode = "200", description = "Solicitud aceptada",
                            content = @Content(schema = @Schema(implementation = ConsultaTransaccionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Argumento(s) de la solicitud invalido(s)",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno en el servicio",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @RequestValidation(
            pathVariables = {@PathVariableCheck(name = "nro-cuenta", regularExpression = "")}
    )
    ResponseEntity consultaTransacciones(@PathVariable("nro-cuenta") String nroCuenta);

}