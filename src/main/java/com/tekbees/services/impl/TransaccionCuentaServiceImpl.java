package com.tekbees.services.impl;

import com.tekbees.domain.TransaccionCuenta;
import com.tekbees.dto.MensajeDTO;
import com.tekbees.mapper.TransaccionCuentaMapper;
import com.tekbees.services.TransaccionCuentaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Service
public class TransaccionCuentaServiceImpl implements TransaccionCuentaService {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public MensajeDTO save(TransaccionCuenta transaccionCuenta) {
        try {
            String sql = String.format("INSERT INTO TRANSACCION_CUENTA (idTransaccion, idCuenta) VALUES ('%d', '%d')",
                    transaccionCuenta.getIdTransaccion(), transaccionCuenta.getIdCuenta());
            jdbcTemplate.execute(sql);
            return new MensajeDTO("1", "OK");
        } catch (Exception e) {
            logger.error(e);
            return new MensajeDTO("0", null);
        }
    }

    @Override
    public MensajeDTO update(TransaccionCuenta transaccionCuenta) {
        if(transaccionCuenta.getIdTransaccion() != null ) {
            String sql = String.format("UPDATE TRANSACCION_CUENTA SET idTransaccion='%d', idCuenta='%s' "
                            + "WHERE idTransaccion='%d'",
                    transaccionCuenta.getIdTransaccion(), transaccionCuenta.getIdCuenta(), transaccionCuenta.getIdTransaccion());
            jdbcTemplate.execute(sql);
            return new MensajeDTO("1", "OK");
        }
        return new MensajeDTO("0", null);
    }

    @Override
    public List<TransaccionCuenta> findAll() {
        return jdbcTemplate.query("SELECT * FROM TRANSACCION_CUENTA", new TransaccionCuentaMapper());
    }

    @Override
    public TransaccionCuenta findById(int idTransaccion) {
        return jdbcTemplate.queryForObject("SELECT * FROM TRANSACCION_CUENTA WHERE idTransaccion = " + idTransaccion, new TransaccionCuentaMapper());
    }
}
