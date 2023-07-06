package com.tekbees.controller;

import com.tekbees.common.RepBase;
import com.tekbees.domain.Login;
import com.tekbees.domain.Usuario;
import com.tekbees.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RepBase> signIn(@RequestBody Login login) {
        return ResponseEntity.ok(new RepBase(authService.signIn(login)));
    }

    @PostMapping("/register")
    public ResponseEntity<RepBase> register(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(new RepBase(authService.register(usuario)));
    }
}
