package com.tekbees.controller;

import com.tekbees.common.RepBase;
import com.tekbees.domain.Transaccion;
import com.tekbees.domain.TransaccionCuenta;
import com.tekbees.services.TransaccionCuentaService;
import com.tekbees.services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaccionCuenta")
public class TransaccionCuentaRestController {

    @Autowired
    private TransaccionCuentaService transaccionCuentaService;

    @GetMapping
    public ResponseEntity<List<TransaccionCuenta>> findAll() {
        return ResponseEntity.ok(transaccionCuentaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionCuenta> findById(@PathVariable int id) {
        return ResponseEntity.ok(transaccionCuentaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RepBase> save(@RequestBody TransaccionCuenta transaccionCuenta) {
        return ResponseEntity.ok(new RepBase(transaccionCuentaService.save(transaccionCuenta)));
    }

    @PutMapping
    public ResponseEntity<RepBase> update(@RequestBody TransaccionCuenta transaccionCuenta) {
        return ResponseEntity.ok(new RepBase(transaccionCuentaService.update(transaccionCuenta)));
    }
}
