package br.com.bytebank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection recuperaConexao(){
        try{
            return DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/bytebank?user=postgres&password=1440");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
