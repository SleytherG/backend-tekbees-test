package com.tekbees.services;

import com.tekbees.domain.Login;
import com.tekbees.domain.Usuario;
import com.tekbees.dto.MensajeDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthService {

    public MensajeDTO signIn(Login login);

    public MensajeDTO register(Usuario usuario);
}
