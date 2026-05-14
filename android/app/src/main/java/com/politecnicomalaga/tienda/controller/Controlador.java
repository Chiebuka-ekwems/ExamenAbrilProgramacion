package com.politecnicomalaga.tienda.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.politecnicomalaga.tienda.MainActivity;
import com.politecnicomalaga.tienda.dataservice.BBDDAccess;
import com.politecnicomalaga.tienda.model.*;

import java.lang.reflect.Type;
import java.util.*;


public class Controlador {
    private MainActivity miPantalla;
    private List<Producto> products;
    private List<Cliente> clientes; // Añadimos la lista de clientes
    private boolean mostrandoClientes = false; // Chivato para saber qué dato hemos pedido

    private static Controlador singleton;

    private Controlador(MainActivity miPantalla) {
        this.miPantalla = miPantalla;
        products = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    public static Controlador getSingleton(MainActivity miPantalla) {
        if (singleton == null) singleton = new Controlador(miPantalla);
        return singleton;
    }

    /*public void addProduct(Map<String,String> datos) {
        boolean resultado = true;
        Producto p = new Producto(datos.get("code"),datos.get("descripcion"),Float.parseFloat(datos.get("precio")));

        resultado = products.add(p);
        if (resultado) {
            BBDDAccess miBBDD = new BBDDAccess(this);
            miBBDD.insertarProducto(datos.get("code"), datos.get("descripcion"), Double.parseDouble(datos.get("precio")), Integer.parseInt(datos.get("stock")));
        }
    }*/

    public void listarTodos() {
        mostrandoClientes = false; // Indicamos que buscamos productos
        BBDDAccess miBBDD = new BBDDAccess(this);
        miBBDD.listarTodos();
    }

    public void listarClientes() {
        mostrandoClientes = true; // Indicamos que buscamos clientes
        BBDDAccess miBBDD = new BBDDAccess(this);
        miBBDD.listarClientes();
    }

    public List<Map<String,String>> getData() {
        List<Map<String,String>> resultado = new ArrayList<>();

        if (mostrandoClientes) {
            for(Cliente c: clientes) {
                Map<String,String> map = new HashMap<>();
                map.put("tipo", "cliente"); // Identificador
                map.put("dni", c.getDni());
                map.put("nombre", c.getNombre());
                map.put("apellidos", c.getApellidos());
                map.put("telefono", c.getTelefono());
                resultado.add(map);
            }
        } else {
            for(Producto p: products) {
                Map<String,String> map = new HashMap<>();
                map.put("tipo", "producto"); // Identificador
                map.put("c", ""+p.getId_producto());
                map.put("d", p.getDescripcion());
                map.put("p", ""+p.getPrecio_unitario());
                resultado.add(map);
            }
        }
        return resultado;
    }

    public void setData(String jsonData, boolean error) {
        try {
            JsonParser.parseString(jsonData);
        } catch (JsonSyntaxException e) {
            error = true;
        }

        if (!error) {
            Gson gson = new Gson();

            // Elegimos cómo parsear el JSON según el botón que se pulsó
            if (mostrandoClientes) {
                clientes.clear();
                Type tipoListaClientes = new TypeToken<List<Cliente>>(){}.getType();
                clientes = gson.fromJson(jsonData, tipoListaClientes);
            } else {
                products.clear();
                Type tipoListaProductos = new TypeToken<List<Producto>>(){}.getType();
                products = gson.fromJson(jsonData, tipoListaProductos);
            }
            this.miPantalla.reaccionar("");
        } else {
            this.miPantalla.reaccionar("Error de acceso a Backend " + jsonData);
        }
    }
}