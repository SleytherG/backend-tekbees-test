package com.tekbees.mapper;

import com.tekbees.domain.Cuenta;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public class CuentaMapper implements RowMapper<Cuenta> {

    @Override
    public Cuenta mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(rs.getInt("idCuenta"));
        cuenta.setIdUsuario(rs.getInt("idUsuario"));
        cuenta.setNroCuenta(rs.getString("nroCuenta"));
        cuenta.setFechaCreacion(rs.getDate("fechaCreacion"));
        cuenta.setFondos(rs.getInt("fondos"));
        return cuenta;
    }
}
