package com.politecnicomalaga.tienda.controller;



import com.google.gson.*;
import com.politecnicomalaga.tienda.dataservice.BBDDAccess;
import com.politecnicomalaga.tienda.model.*;

import java.sql.SQLException;
import java.util.*;


public class Controlador implements DataAccess{

    private BBDDAccess miBBDD;

    public Controlador() {
        miBBDD = new BBDDAccess();
    }

    //Implementar lógica definida en el interfaz DataAccess para que los Servlets soliciten lo que quieran
    @Override
    public String listAllProductos(){
    return "Por hacer";
    };

    @Override
    public String findProductoXCodigo(String code){
        return "Por hacer";
    };

    @Override
    public String findClienteXDNI(String dni){
        return "Por hacer";
    };

    @Override
    public String listProductosXPedido(String dni, String pedido){
        return "Por hacer";
    };

    @Override
    public String importData(String jsonDataFromCSV){
        BBDDAccess bbdd = new BBDDAccess();
        JsonObject jsonObject = JsonParser.parseString(jsonDataFromCSV).getAsJsonObject();


        //Primero Clientes
        JsonArray arrayClientes = jsonObject.getAsJsonArray("clientes");
        for(JsonElement elemento : arrayClientes){
            JsonObject jsonCliente = elemento.getAsJsonObject();

            String dni = jsonCliente.get("dni").getAsString();
            String nombre = jsonCliente.get("nombre").getAsString();
            String apellidos = jsonCliente.get("apellidos").getAsString();
            String email = jsonCliente.get("email").getAsString();
            String telefono = jsonCliente.get("telefono").getAsString();
            String direccion = jsonCliente.get("direccion").getAsString();

            try{
                bbdd.insertarCliente(new Cliente(dni, nombre, apellidos, email, telefono, direccion));

            }catch (SQLException se){
                // 1. Imprime el error exacto en la consola de Tomcat/Docker
                System.err.println("Error de Base de Datos al insertar el cliente " + dni + ": " + se.getMessage());
                se.printStackTrace();
                //return "ERROR";


            }catch (ClassNotFoundException ce){
                // 1. Imprime el error si falta el driver de MySQL
                System.err.println("Error: No se encuentra el Driver de MySQL: " + ce.getMessage());
                ce.printStackTrace();

                //return "ERROR";

            }

        }

        //Productos
        JsonArray arrayProductos = jsonObject.getAsJsonArray("productos");
        for(JsonElement elemento : arrayProductos){
            JsonObject jsonProducto = elemento.getAsJsonObject();

            int id_producto = jsonProducto.get("id_producto").getAsInt();
            String descripcion = jsonProducto.get("descripcion").getAsString();
            float precio_unitario = jsonProducto.get("precio_unitario").getAsFloat();

            try{
                bbdd.insertarProducto(new Producto(id_producto, descripcion, precio_unitario));

            }catch (SQLException se){
                // 1. Imprime el error exacto en la consola de Tomcat/Docker
                System.err.println("Error de Base de Datos al insertar el producto " + id_producto + ": " + se.getMessage());
                se.printStackTrace();
                //return "ERROR";


            }catch (ClassNotFoundException ce){
                // 1. Imprime el error si falta el driver de MySQL
                System.err.println("Error: No se encuentra el Driver de MySQL: " + ce.getMessage());
                ce.printStackTrace();

                //return "ERROR";

            }

        }


        //Pedidos
        JsonArray arrayPedidos = jsonObject.getAsJsonArray("pedidos");
        for(JsonElement elemento : arrayPedidos){
            JsonObject jsonPedido = elemento.getAsJsonObject();

            int id_pedido = jsonPedido.get("id_pedido").getAsInt();
            String dni_cliente = jsonPedido.get("dni_cliente").getAsString();
            String fecha_pedido = jsonPedido.get("fecha_pedido").getAsString();
            int num_lineas = jsonPedido.get("num_lineas").getAsInt();
            float total_pedido = jsonPedido.get("total_pedido").getAsFloat();

            try{
                bbdd.insertarPedido(new Pedido(id_pedido, dni_cliente, fecha_pedido, num_lineas, total_pedido));

            }catch (SQLException se){
                // 1. Imprime el error exacto en la consola de Tomcat/Docker
                System.err.println("Error de Base de Datos al insertar el pedido " + id_pedido + ": " + se.getMessage());
                se.printStackTrace();
                //return "ERROR";


            }catch (ClassNotFoundException ce){
                // 1. Imprime el error si falta el driver de MySQL
                System.err.println("Error: No se encuentra el Driver de MySQL: " + ce.getMessage());
                ce.printStackTrace();

                //return "ERROR";

            }

        }

        //Lineas_Pedidos

        JsonArray arrayLineasPedidos = jsonObject.getAsJsonArray("pedidos");
        for(JsonElement elemento1 : arrayLineasPedidos) {
                JsonObject jsonPedido = elemento1.getAsJsonObject();

            // Extraer el array de líneas de pedido que se encuentra dentro de cada pedido
                if (jsonPedido.has("misproductos") && !jsonPedido.get("misproductos").isJsonNull()) {
                JsonArray arrayLineas = jsonPedido.getAsJsonArray("misproductos");

                for(JsonElement elementoLinea : arrayLineas){

                    JsonObject jsonLinea = elementoLinea.getAsJsonObject();

                    int id_linea = jsonLinea.get("id_linea").getAsInt();
                    int id_pedido = jsonLinea.get("id_pedido").getAsInt();
                    int id_producto = jsonLinea.get("id_producto").getAsInt();
                    int cantidad = jsonLinea.get("cantidad").getAsInt();
                    float precio_unitario = jsonLinea.get("precio_unitario").getAsFloat();

                    try {
                        bbdd.insertarLineaPedido(new Linea_pedido(id_linea, id_pedido, id_producto, cantidad, precio_unitario,null));

                    } catch (SQLException se) {
                        // 1. Imprime el error exacto en la consola de Tomcat/Docker
                        System.err.println("Error de Base de Datos al insertar la Linea de pedido " + id_linea + ": " + se.getMessage());
                        se.printStackTrace();
                        return "ERROR";


                    } catch (ClassNotFoundException ce) {
                        // 1. Imprime el error si falta el driver de MySQL
                        System.err.println("Error: No se encuentra el Driver de MySQL: " + ce.getMessage());
                        ce.printStackTrace();

                        return "ERROR";

                    }
                }
            }

        }

        return "Okay";


    }
}