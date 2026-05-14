package com.politecnicomalaga.tienda.dataservice;

import com.politecnicomalaga.tienda.model.Cliente;
import com.politecnicomalaga.tienda.model.Linea_pedido;
import com.politecnicomalaga.tienda.model.Pedido;
import com.politecnicomalaga.tienda.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BBDDAccess {


    //Aquí los métodos necesarios para CRUD de datos en la BBDD

    public void insertarCliente(Cliente c) throws SQLException, ClassNotFoundException {

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

    public void insertarProducto(Producto p) throws SQLException, ClassNotFoundException {

        PreparedStatement pstmt = null;
        Connection conn = ConexionBD.getConnection();

        String tabla = "productos";
        String values = " (id_producto, descripcion, precio_unitario) VALUES (?, ?, ?)";

        String sql = "INSERT INTO " + tabla + values;


        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, p.getId_producto());
        pstmt.setString(2, p.getDescripcion());
        pstmt.setFloat(3, p.getPrecio_unitario());


        pstmt.executeUpdate();
    }

    public void insertarPedido(Pedido p) throws SQLException, ClassNotFoundException {

        PreparedStatement pstmt = null;
        Connection conn = ConexionBD.getConnection();

        String tabla = "pedidos";
        String values = " (id_pedido, dni_cliente, fecha_pedido, num_lineas, total_pedido) VALUES (?, ?, ?, ?, ?)";

        String sql = "INSERT INTO " + tabla + values;


        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, p.getId_pedido());
        pstmt.setString(2, p.getDni_cliente());
        pstmt.setString(3, p.getFecha_pedido());
        pstmt.setInt(4, p.getNum_lineas());
        pstmt.setFloat(5, p.getTotal_pedido());


        pstmt.executeUpdate();
    }

    public void insertarLineaPedido(Linea_pedido p) throws SQLException, ClassNotFoundException {

        PreparedStatement pstmt = null;
        Connection conn = ConexionBD.getConnection();

        String tabla = "lineas_pedido";
        String values = " (id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        String sql = "INSERT INTO " + tabla + values;


        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, p.getId_pedido());
        pstmt.setInt(2, p.getId_producto());
        pstmt.setInt(3, p.getCantidad());
        pstmt.setFloat(4, p.getPrecio_unitario());


        pstmt.executeUpdate();
    }

    public List<Producto> listarTodos() throws SQLException, ClassNotFoundException {

        Connection conn = null;
        List<Producto> listaResultado = new ArrayList<>();

        conn = ConexionBD.getConnection();

        // Productos normales
        String sql = "SELECT id_producto, descripcion, precio_unitario from productos";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            listaResultado.add(new Producto(rs.getInt("id_producto"),
                    rs.getString("descripcion"),
                    rs.getFloat("precio_unitario")));
        }
        return listaResultado;
    }

    public Producto buscarProductoxCode(int codigo) throws SQLException, ClassNotFoundException{
        Connection conn = null;
        PreparedStatement pstmt = null;

        conn = ConexionBD.getConnection();

        // Productos normales
        String sql = "SELECT id_producto, descripcion, precio_unitario from productos where id_producto=?";

        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, codigo);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            return (new Producto(rs.getInt("id_producto"),
                    rs.getString("descripcion"),
                    rs.getFloat("precio_unitario")));
        }
        return null;
    }


    public List<Cliente> buscarClientexDni(String dni) throws SQLException, ClassNotFoundException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        List<Cliente> listaResultado = new ArrayList<>();

        conn = ConexionBD.getConnection();
        String sql="";

        // Productos normales
        if(dni!=null){
             sql = "SELECT dni, nombre, apellidos, email, telefono, direccion from clientes where dni=?";
        }else{
            sql = "SELECT dni, nombre, apellidos, email, telefono, direccion from clientes";
        }


        pstmt = conn.prepareStatement(sql);
        if(dni!=null) {
            pstmt.setString(1, dni);
        }

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            listaResultado.add(new Cliente(rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("direccion")));
        }
        return listaResultado;

    }

    public List<Producto> listarProductoxPedido(String dni, String pedido) throws SQLException, ClassNotFoundException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        List<Producto> listaResultado = new ArrayList<>();

        conn = ConexionBD.getConnection();

        // Productos normales
        String sql = "select p.id_producto, p.descripcion, p.precio_unitario " +
                "from productos p " +
                "inner join lineas_pedido le on p.id_producto = le.id_producto " +
                "inner join pedidos pe on le.id_pedido = pe.id_pedido " +
                "where le.id_pedido = ? and pe.dni_cliente = ?";



        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, pedido);
        pstmt.setString(2, dni);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            listaResultado.add(new Producto(rs.getInt("id_producto"),
                    rs.getString("descripcion"),
                    rs.getFloat("precio_unitario")));
        }
        return listaResultado;
    }



}