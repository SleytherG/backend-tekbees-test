package com.tekbees.services.impl;

import com.tekbees.domain.Login;
import com.tekbees.domain.Usuario;
import com.tekbees.dto.MensajeDTO;
import com.tekbees.services.AuthService;
import com.tekbees.services.UsuarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Service
public class AuthServiceImpl implements AuthService {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @PostConstruct
    public void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MensajeDTO signIn(Login login) {
        Usuario usuario = usuarioService.findByUsername(login.getUsername());
        if ( usuario != null && usuario.getUsername().equalsIgnoreCase(login.getUsername()) && usuario.getPassword().equals(login.getPassword())) {
            return new MensajeDTO("1", "Inicio de Sesión Exitoso");
        }
        return new MensajeDTO("0", "Username Invalido");
    }

    @Override
    public MensajeDTO register(Usuario usuario) {
        Usuario usuarioExistente = usuarioService.findByUsername(usuario.getUsername());
        if ( usuarioExistente != null && usuarioExistente.getUsername().equalsIgnoreCase(usuario.getUsername())) {
            return new MensajeDTO("0", "El username ya existe");
        }
        if ( usuarioExistente == null) {
            try {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setUsername(usuario.getUsername());
                nuevoUsuario.setPassword(usuario.getPassword());
                nuevoUsuario.setNombres(usuario.getNombres());
                nuevoUsuario.setApellidos(usuario.getApellidos());
                usuarioService.save(nuevoUsuario);
                return new MensajeDTO("1", "Usuario creado exitosamente.");
            }catch (Exception ex) {
                return new MensajeDTO("0", "No se pudo crear el usuario.");
            }
        } else {
            return new MensajeDTO("0", "No se pudo crear el usuario.");
        }
    }
}
