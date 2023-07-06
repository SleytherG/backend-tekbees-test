package com.tekbees.controller;

import com.tekbees.common.RepBase;
import com.tekbees.domain.Transaccion;
import com.tekbees.dto.TransferToAccountDTO;
import com.tekbees.services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransaccionRestController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping
    public ResponseEntity<List<Transaccion>> findAll() {
        return ResponseEntity.ok(transaccionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> findById(@PathVariable int id) {
        return ResponseEntity.ok(transaccionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RepBase> save(@RequestBody Transaccion transaccion) {
        return ResponseEntity.ok(new RepBase(transaccionService.save(transaccion)));
    }

    @PutMapping
    public ResponseEntity<RepBase> update(@RequestBody Transaccion transaccion) {
        return ResponseEntity.ok(new RepBase(transaccionService.update(transaccion)));
    }

    @GetMapping("/byUser/{idCuenta}")
    public List<Transaccion> getAccountsByUser(@PathVariable Integer idCuenta) {
        return transaccionService.getTransactionsByUser(idCuenta);
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<RepBase> transferMoneyToAccount(@RequestBody TransferToAccountDTO transferToAccountDTO) {
        return ResponseEntity.ok(new RepBase(transaccionService.transferMoney(transferToAccountDTO)));
    }

}
