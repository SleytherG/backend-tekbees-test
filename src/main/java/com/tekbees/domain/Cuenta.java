package com.tekbees.domain;

import java.util.Date;

public class Cuenta {
    private Integer idCuenta;
    private Integer idUsuario;
    private String nroCuenta;
    private Date fechaCreacion;
    private Integer fondos;

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getFondos() {
        return fondos;
    }

    public void setFondos(Integer fondos) {
        this.fondos = fondos;
    }
}
