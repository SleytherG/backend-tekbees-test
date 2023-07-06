package com.tekbees.controller;

import com.tekbees.common.RepBase;
import com.tekbees.domain.Cuenta;
import com.tekbees.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class CuentaRestController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<Cuenta>> findAll() {
        return ResponseEntity.ok(cuentaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> findById(@PathVariable int id) {
        return ResponseEntity.ok(cuentaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RepBase> save(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(new RepBase(cuentaService.save(cuenta)));
    }

    @PutMapping
    public ResponseEntity<RepBase> update(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(new RepBase(cuentaService.update(cuenta)));
    }

    @GetMapping("/byUser/{username}")
    public List<Cuenta> getAccountsByUser(@PathVariable String username) {
        return (cuentaService.getAccountsByUser(username));
    }
}
