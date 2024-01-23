package com.bisa.cam.datasources;

import com.bisa.cam.business.repositories.TransaccionRepository;
import com.bisa.cam.domain.Cuenta;
import com.bisa.cam.domain.ResultadoTransaccion;
import com.bisa.cam.domain.Transaccion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConexionMysql implements TransaccionRepository {

    private final Logger logger = LogManager.getLogger(getClass());

    public static DataSource getDataSource(final String user, final String pwd) {

        LogManager.getLogger().info("Obteniendo DataSource...");

        PoolConfiguration config = new PoolProperties();
        config.setMaxActive(100);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        final String connectionUrl = "jdbc:mysql://localhost:3306/dbtransacion";
        LogManager.getLogger().info("URL={}", connectionUrl);
        config.setUrl(connectionUrl);
        config.setUsername(user);
        config.setPassword(pwd);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(config);
        return dataSource;
    }


    @Override
    public List<Transaccion> consultaTransacciones(Long numCuenta) throws Exception {
        String user = "root";
        String pwd = "desa";
        String query = "select idTrx,fecha,nroCuenta,idCliente,operacion,\n" +
                " importe,moneda,cambio,tipoCambio,estado,codAtm,error\n" +
                " from dbtransacion.transaccion\n" +
                " where  nroCuenta = ?\n";
        logger.info("CONSULTANDO >>>>" +  " MYSQL:");

        DataSource dataSource = getDataSource(user, pwd);
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Transaccion transac = null;
        List<Transaccion> listaTransac = new ArrayList<Transaccion>();
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(query);
            cs.setLong(1, numCuenta);
            logger.info("CONSULTANDO >>>>" +  " SQL:" + cs);
            rs = cs.executeQuery();
            while (rs.next()) {
                logger.info("RESULTADO SQL>>>>" + rs.getString(1));
                transac = new Transaccion();
                transac.setIdTrx(rs.getLong("idTrx"));
                transac.setEstado(rs.getString("estado"));
                transac.setNroCuenta(rs.getLong("nroCuenta"));
                transac.setCodAtm(rs.getString("codAtm"));
                transac.setIdCliente(rs.getLong("idCliente"));
                transac.setImporte(rs.getDouble("importe"));
                transac.setMoneda(rs.getString("moneda"));
                transac.setCambio(rs.getDouble("cambio"));
                transac.setTipoCambio(rs.getString("tipoCambio"));
                transac.setOperacion(rs.getString("operacion"));
                transac.setFecha(rs.getTimestamp("fecha"));
                transac.setError(rs.getString("error"));
                logger.info("OBJETO CUENTA>>>>" + transac);
                listaTransac.add(transac);
            }

        } catch (SQLException e) {
            logger.info("<SQLException>>>>>", e);
            throw new Exception(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return listaTransac;
    }

    @Override
    public ResultadoTransaccion realizarRetiro(Transaccion transaccion) throws Exception {
        String user = "root";
        String pwd = "desa";
        Double cambioDia = 6.96;
        String tipoCambio = "V"; // Venta / Compra
        String query = "INSERT INTO dbtransacion.transaccion\n" +
                "(fecha,  nroCuenta,  idCliente,  operacion,  importe,  moneda,  \n" +
                "cambio,  tipoCambio,  estado,  codAtm,  error)\n" +
                "VALUES(current_timestamp, ?, ?, 'RET', ?, ?,\n" +
                "?, ?, ?, ?, ?);";
        logger.info("CONSULTANDO >>>>" +  " MYSQL:");
        DataSource dataSource = getDataSource(user, pwd);
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Boolean registrado = false;
        ResultadoTransaccion resultado = new ResultadoTransaccion();
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(query);
            cs.setLong(1, transaccion.getNroCuenta());
            cs.setLong(2, transaccion.getNroCuenta());
            cs.setDouble(3, transaccion.getImporte());
            cs.setString(4, transaccion.getMoneda());
            cs.setDouble(5, cambioDia);
            cs.setString(6, tipoCambio);
            cs.setString(7, "P");
            cs.setString(8, transaccion.getCodAtm());
            cs.setString(9, "");
            logger.info("CONSULTANDO >>>>" +  " SQL:" + cs);

            int res = cs.executeUpdate();
            if(res>0) {
                resultado.setResultado(true);
                resultado.setDescripcion("Retiro Procesado");
            }else{
                resultado.setResultado(false);
                resultado.setDescripcion("Retiro NO Procesado");
            }

        } catch (SQLException e) {
            logger.info("<SQLException>>>>>", e);
            throw new Exception(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return resultado;
    }

    @Override
    public ResultadoTransaccion realizarDeposito(Transaccion transaccion) throws Exception {
        String user = "root";
        String pwd = "desa";
        Double cambioDia = 6.96;
        String tipoCambio = "V"; // Venta / Compra
        String query = "INSERT INTO dbtransacion.transaccion\n" +
                "(fecha,  nroCuenta,  idCliente,  operacion,  importe,  moneda,  \n" +
                "cambio,  tipoCambio,  estado,  codAtm,  error)\n" +
                "VALUES(current_timestamp, ?, ?, 'DEP', ?, ?,\n" +
                "?, ?, ?, ?, ?);";
        logger.info("CONSULTANDO >>>>" +  " MYSQL:");
        DataSource dataSource = getDataSource(user, pwd);
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Boolean registrado = false;
        ResultadoTransaccion resultado = new ResultadoTransaccion();
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(query);
            cs.setLong(1, transaccion.getNroCuenta());
            cs.setLong(2, transaccion.getNroCuenta());
            cs.setDouble(3, transaccion.getImporte());
            cs.setString(4, transaccion.getMoneda());
            cs.setDouble(5, cambioDia);
            cs.setString(6, tipoCambio);
            cs.setString(7, "P");
            cs.setString(8, transaccion.getCodAtm());
            cs.setString(9, "");
            logger.info("CONSULTANDO >>>>" +  " SQL:" + cs);

            int res = cs.executeUpdate();
            if(res>0) {
                resultado.setResultado(true);
                resultado.setDescripcion("Deposito Procesado");
            }else{
                resultado.setResultado(false);
                resultado.setDescripcion("Deposito NO Procesado");
            }

        } catch (SQLException e) {
            logger.info("<SQLException>>>>>", e);
            throw new Exception(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return resultado;

    }

    @Override
    public ResultadoTransaccion realizarConversion(Transaccion transaccionOrigen, String monedaDestino) throws Exception {
        return null;
    }
}
