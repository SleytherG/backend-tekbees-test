package com.tekbees.services;

import com.tekbees.common.RepBase;
import com.tekbees.domain.Transaccion;
import com.tekbees.dto.MensajeDTO;
import com.tekbees.dto.TransferToAccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionService extends BaseService<Transaccion> {
    List<Transaccion> getTransactionsByUser(Integer idCuenta);

    MensajeDTO transferMoney(TransferToAccountDTO transferToAccountDTO);
}
