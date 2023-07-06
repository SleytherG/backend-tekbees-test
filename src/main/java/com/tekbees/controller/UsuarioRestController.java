package com.tekbees.controller;


import com.tekbees.common.RepBase;
import com.tekbees.domain.Cuenta;
import com.tekbees.domain.Usuario;
import com.tekbees.services.CuentaService;
import com.tekbees.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable int id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RepBase> save(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(new RepBase(usuarioService.save(usuario)));
    }

    @PutMapping
    public ResponseEntity<RepBase> update(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(new RepBase(usuarioService.update(usuario)));
    }
}
