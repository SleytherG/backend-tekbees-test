package com.tekbees.mapper;

import com.tekbees.domain.Transaccion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public class TransaccionMapper implements RowMapper<Transaccion> {
    @Override
    public Transaccion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaccion transaccion = new Transaccion();
        transaccion.setIdTransaccion(rs.getInt("idTransaccion"));
        transaccion.setFechaOperacion(rs.getDate("fechaOperacion"));
        transaccion.setDescripcion(rs.getString("descripcion"));
        transaccion.setIdCuenta(rs.getInt("idCuenta"));
        transaccion.setMonto(rs.getInt("monto"));
        return transaccion;
    }
}
