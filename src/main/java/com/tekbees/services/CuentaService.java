package com.tekbees.services;


import com.tekbees.domain.Cuenta;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaService extends BaseService<Cuenta> {

    public Cuenta findByNroCuenta(String nroCuenta);

    public List<Cuenta> getAccountsByUser(String username);
}
