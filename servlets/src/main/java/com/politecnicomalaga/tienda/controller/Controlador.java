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


            }catch (ClassNotFoundException ce){

            }

        }

        JsonArray arrayProductos = jsonObject.getAsJsonArray("productos");
        JsonArray arrayPedidos = jsonObject.getAsJsonArray("pedidos");

        //Primero Clientes



        //Productos


        //Pedidos

        return "Por hacer";
    };




}