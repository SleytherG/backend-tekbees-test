package com.tekbees.services;

import com.tekbees.domain.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioService extends BaseService<Usuario> {

    public Usuario findByUsername(String username);
}
