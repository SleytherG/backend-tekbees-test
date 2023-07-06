package com.tekbees.mapper;
import com.tekbees.domain.TransaccionCuenta;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public class TransaccionCuentaMapper implements RowMapper<TransaccionCuenta> {

    @Override
    public TransaccionCuenta mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransaccionCuenta transaccionCuenta = new TransaccionCuenta();
        transaccionCuenta.setIdTransaccion(rs.getInt("idTransaccion"));
        transaccionCuenta.setIdCuenta(rs.getInt("idCuenta"));
        return transaccionCuenta;
    }
}
