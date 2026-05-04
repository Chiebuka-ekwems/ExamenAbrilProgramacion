package com.politecnicomalaga.tienda.dataservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    //Programar esta clase con respecto a la infraestructura disponible
    private static final String URL = "jdbc:mysql://192.168.10.174:3308/tienda";
    private static final String USER = "tienda_user";
    private static final String PASSWORD = "onlyforyoureyes";
    private static final String CLASSNAME = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}