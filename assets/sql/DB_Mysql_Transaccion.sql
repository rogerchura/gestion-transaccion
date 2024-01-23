

/**Ejectuar con usuario  root/desa  */
CREATE DATABASE dbtransacion;

SHOW DATABASES;

USE dbtransacion;

CREATE TABLE dbtransacion.transaccion (
    idTrx INT AUTO_INCREMENT PRIMARY KEY,
    fecha TIMESTAMP,    
    nroCuenta DECIMAL(10, 0),
    idCliente INT,
    operacion VARCHAR(3),
    importe DECIMAL(12, 2),
    moneda VARCHAR(3),
    cambio DECIMAL(12, 2),
    tipoCambio VARCHAR(3),
    estado VARCHAR(3),     
    codAtm VARCHAR(10),
    error VARCHAR(50)
);


INSERT INTO dbtransacion.transaccion
(fecha,  nroCuenta,  idCliente,  operacion,  importe,  moneda,  
cambio,  tipoCambio,  estado,  codAtm,  error)
VALUES(current_timestamp, 12345, 7000, 'RET', 10, 'BOB',
6.96, 'V', 'P', 'ATM123', '');

INSERT INTO dbtransacion.transaccion
(fecha,  nroCuenta,  idCliente,  operacion,  importe,  moneda,  
cambio,  tipoCambio,  estado,  codAtm,  error)
VALUES(current_timestamp, 12345, 7000, 'DEP', 20, 'USD',
6.96, 'V', 'P', 'ATM123', '');


commit;

select idTrx,fecha,nroCuenta,idCliente,operacion,
importe,moneda,cambio,tipoCambio,estado,codAtm,error
 from dbtransacion.transaccion;


   












