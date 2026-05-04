package com.politecnicomalaga.tienda.dataservice;

import com.politecnicomalaga.tienda.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BBDDAccess {



    //Aquí los métodos necesarios para CRUD de datos en la BBDD

    public void insertarCliente(Cliente c) throws SQLException,ClassNotFoundException {

        PreparedStatement pstmt = null;
        Connection conn = ConexionBD.getConnection();

        String tabla = "clientes";
        String values = " (dni, nombre, apellidos, email, telefono, direccion) VALUES (?, ?, ?, ?, ?, ?)";

        String sql = "INSERT INTO " + tabla + values;


        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, c.getDni());
        pstmt.setString(2, c.getNombre());
        pstmt.setString(3, c.getApellidos());
        pstmt.setString(4, c.getEmail());
        pstmt.setString(5, c.getTelefono());
        pstmt.setString(6, c.getDireccion());


        pstmt.executeUpdate();
    }
}