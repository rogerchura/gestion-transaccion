/**
 *
 * La capa de transporte (transport layer) puede desencadenar un interactor para realizar la lógica de negocio.
 *
 * Lo tratamos como una entrada para nuestro sistema. La capa de transporte más común para los microservicios
 * es la capa API HTTP y un conjunto de controladores que manejan las solicitudes.
 *
 * Al tener la lógica de negocio extraída en los interactores, no estamos acoplados a una capa de transporte o
 * implementación de controlador en particular.
 * Los interactores pueden ser activados no sólo por un controlador, sino también por un evento, un trabajo de cron,
 * o desde la línea de comandos.
 *
 */
package com.bisa.cam.transport;