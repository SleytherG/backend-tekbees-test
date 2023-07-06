package com.tekbees.services.impl;

import com.tekbees.domain.Usuario;
import com.tekbees.dto.MensajeDTO;
import com.tekbees.mapper.UsuarioMapper;
import com.tekbees.services.UsuarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public MensajeDTO save(Usuario usuario) {
        try {
            String sql = String.format("INSERT INTO USUARIO (nombres, apellidos, username, password) VALUES ('%s', '%s', '%s', '%s')",
                    usuario.getNombres(), usuario.getApellidos(), usuario.getUsername(), usuario.getPassword());
            jdbcTemplate.execute(sql);
            return new MensajeDTO("1", "OK");
        } catch (Exception e) {
            logger.error(e);
            return new MensajeDTO("0", null);
        }
    }

    @Override
    public MensajeDTO update(Usuario usuario) {
        if(usuario.getIdUsuario() != null ) {
            String sql = String.format("UPDATE USUARIO SET nombres='%s', apellidos='%s', username='%s', password='%s' "
                            + "WHERE idUsuario='%s'",
                    usuario.getNombres(), usuario.getApellidos(), usuario.getUsername(), usuario.getPassword(), usuario.getIdUsuario());
            jdbcTemplate.execute(sql);
            return new MensajeDTO("1", "OK");
        }
        return new MensajeDTO("0", null);
    }

    @Override
    public List<Usuario> findAll() {
        return jdbcTemplate.query("SELECT * FROM USUARIO", new UsuarioMapper());
    }

    @Override
    public Usuario findById(int idUsuario) {
        return jdbcTemplate.queryForObject("SELECT * FROM USUARIO WHERE idUsuario = " + idUsuario, new UsuarioMapper());
    }

    @Override
    public Usuario findByUsername(String username)  {
        String sql = "SELECT * FROM USUARIO WHERE lower(username) = lower(?)";
        Object[] params = { username };
        try {
            return jdbcTemplate.queryForObject(sql, params , new UsuarioMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
