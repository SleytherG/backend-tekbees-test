package com.tekbees.services.impl;

import com.tekbees.domain.Cuenta;
import com.tekbees.domain.Usuario;
import com.tekbees.dto.MensajeDTO;
import com.tekbees.mapper.CuentaMapper;
import com.tekbees.services.CuentaService;
import com.tekbees.services.UsuarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

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

    @Override
    public MensajeDTO save(Cuenta cuenta) {
        Cuenta cuentaExistente = findByNroCuenta(cuenta.getNroCuenta());
        if ( cuentaExistente != null && cuentaExistente.getNroCuenta().equals(cuenta.getNroCuenta())) {
            return new MensajeDTO("0", "Número de cuenta ya existente");
        }

        if ( cuentaExistente == null) {
            try {
                String sql = String.format("INSERT INTO CUENTA (idUsuario, nroCuenta, fechaCreacion) VALUES ('%d', '%s', '%tF')",
                        cuenta.getIdUsuario(), cuenta.getNroCuenta(), new Date());
                jdbcTemplate.execute(sql);
                return new MensajeDTO("1", "OK");
            } catch (Exception e) {
                logger.error(e);
                return new MensajeDTO("0", null);
            }
        }
        return new MensajeDTO("0", "No se pudo crear la cuenta");
    }

    @Override
    public MensajeDTO update(Cuenta cuenta) {
        if(cuenta.getIdCuenta() != null ) {
            String sql = String.format("UPDATE CUENTA SET idUsuario='%d', nroCuenta='%s', fechaCreacion='%tF'"
                            + "WHERE idCuenta='%d'",
                    cuenta.getIdUsuario(), cuenta.getNroCuenta(), cuenta.getFechaCreacion(), cuenta.getIdCuenta());
            jdbcTemplate.execute(sql);
            return new MensajeDTO("1", "OK");
        }
        return new MensajeDTO("0", null);
    }

    @Override
    public List<Cuenta> findAll() {
        return jdbcTemplate.query("SELECT * FROM CUENTA", new CuentaMapper());
    }

    @Override
    public Cuenta findById(int idCuenta) {
        Object[] params = { idCuenta };
        return jdbcTemplate.queryForObject("SELECT * FROM CUENTA WHERE idCuenta = ?", params, new CuentaMapper());
    }

    @Override
    public Cuenta findByNroCuenta(String nroCuenta) {
        Object[] params = { nroCuenta };
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM CUENTA WHERE nroCuenta = ?", params , new CuentaMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Cuenta> getAccountsByUser(String username) {
        Usuario usuario = usuarioService.findByUsername(username);
        Object[] params = { usuario.getIdUsuario() };

        return jdbcTemplate.query("SELECT * FROM CUENTA WHERE idUsuario = ?", params, new CuentaMapper());
    }
}
