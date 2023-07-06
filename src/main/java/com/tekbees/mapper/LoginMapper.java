package com.tekbees.mapper;

import com.tekbees.domain.Login;
import com.tekbees.domain.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMapper implements RowMapper<Login> {

    @Override
    public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
        Login login = new Login();
        login.setUsername(rs.getString("username"));
        login.setPassword(rs.getString("password"));
        return login;
    }
}
