package com.tekbees.services.impl;

import com.tekbees.domain.Cuenta;
import com.tekbees.domain.Transaccion;
import com.tekbees.domain.Usuario;
import com.tekbees.dto.MensajeDTO;
import com.tekbees.dto.TransferToAccountDTO;
import com.tekbees.mapper.CuentaMapper;
import com.tekbees.mapper.TransaccionCuentaMapper;
import com.tekbees.mapper.TransaccionMapper;
import com.tekbees.services.CuentaService;
import com.tekbees.services.TransaccionService;
import com.tekbees.services.UsuarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;


@Service
public class TransaccionServiceImpl implements TransaccionService {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public UsuarioService usuarioService;

    @Autowired
    public CuentaService cuentaService;



    @Override
    public MensajeDTO save(Transaccion transaccion) {
        try {
            String sql = String.format("INSERT INTO TRANSACCION (fechaOperacion, descripcion, idCuenta, monto) VALUES ('%tF', '%s', '%d', '%d')",
                    new Date(), transaccion.getDescripcion(), transaccion.getIdCuenta(), transaccion.getMonto());
            jdbcTemplate.execute(sql);
            return new MensajeDTO("1", "OK");
        } catch (Exception e) {
            logger.error(e);
            return new MensajeDTO("0", "No se pudo registrar la transacción");
        }
    }

    @Override
    public MensajeDTO update(Transaccion transaccion) {
        if(transaccion.getIdTransaccion() != null ) {
            String sql = String.format("UPDATE TRANSACCION SET descripcion='%s' "
                            + "WHERE idTransaccion='%d'",
                    transaccion.getDescripcion(), transaccion.getIdTransaccion());
            jdbcTemplate.execute(sql);
            return new MensajeDTO("1", "OK");
        }
        return new MensajeDTO("0", "No se pudo actualizar la transacción");
    }

    @Override
    public List<Transaccion> findAll() {
        return jdbcTemplate.query("SELECT * FROM TRANSACCION", new TransaccionMapper());
    }

    @Override
    public Transaccion findById(int idTransaccion) {
        Object[] params = { idTransaccion };
        return jdbcTemplate.queryForObject("SELECT * FROM TRANSACCION WHERE idTransaccion = ?", params ,new TransaccionMapper());
    }

    @Override
    public List<Transaccion> getTransactionsByUser(Integer idCuenta) {
        Object[] params = { idCuenta };
        return jdbcTemplate.query("SELECT * FROM TRANSACCION WHERE idCuenta = ?", params, new TransaccionMapper());
    }

    @Override
    public MensajeDTO transferMoney(TransferToAccountDTO transferToAccountDTO) {

        if ( transferToAccountDTO.getIdCuentaOrigen().equals(transferToAccountDTO.getIdCuentaDestino())) {
            return new MensajeDTO("0", "No se puede realizar la transferencia a la misma cuenta.");
        }

        Cuenta cuentaOrigen = cuentaService.findById(transferToAccountDTO.getIdCuentaOrigen());
        Cuenta cuentaDestino = cuentaService.findById(transferToAccountDTO.getIdCuentaDestino());

        if ( cuentaOrigen.getFondos() <= transferToAccountDTO.getMonto()) {
            return new MensajeDTO("0", "Fondos insuficientes, ingrese un monto menor a transferir.");
        }

        try {
            int saldoDisponibleOrigen = cuentaOrigen.getFondos() - transferToAccountDTO.getMonto();
            int saldoDisponibleDestino = cuentaDestino.getFondos() + transferToAccountDTO.getMonto();

            Object[] paramsOrigen = { saldoDisponibleOrigen,  cuentaOrigen.getIdCuenta()};
            Object[] paramsDestino = { saldoDisponibleDestino, cuentaDestino.getIdCuenta()};

            String sqlOrigen = String.format("UPDATE CUENTA SET fondos = '%d' WHERE idCuenta = '%d'",
                    saldoDisponibleOrigen, cuentaOrigen.getIdCuenta());
            String sqlDestino = String.format("UPDATE CUENTA SET fondos = '%d' WHERE idCuenta = '%d'",
                    saldoDisponibleDestino, cuentaDestino.getIdCuenta());
            jdbcTemplate.execute(sqlOrigen);
            jdbcTemplate.execute(sqlDestino);


            Transaccion transaccionOrigen = new Transaccion();
            Transaccion transaccionDestino = new Transaccion();

            transaccionOrigen.setIdCuenta(cuentaOrigen.getIdCuenta());
            transaccionOrigen.setDescripcion("Transferencia a cuentas propias");
            transaccionOrigen.setFechaOperacion(new Date());
            transaccionOrigen.setMonto(transferToAccountDTO.getMonto());
            save(transaccionOrigen);

            transaccionDestino.setIdCuenta(cuentaDestino.getIdCuenta());
            transaccionDestino.setDescripcion("Transferencia a cuentas propias");
            transaccionDestino.setFechaOperacion(new Date());
            transaccionDestino.setMonto(transferToAccountDTO.getMonto());
            save(transaccionDestino);

            return new MensajeDTO("1", "Transferencia realizada correctamente");
        } catch (Exception e) {
            return new MensajeDTO("0", "No se pudo realizar la transferencia.");
        }
    }
}
