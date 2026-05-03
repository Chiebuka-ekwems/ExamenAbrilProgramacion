package com.politecnicomalaga;

import com.politecnicomalaga.controller.Controlador;
import com.politecnicomalaga.dataservice.ConexionServlet;

public class Main {
    public static void main(String[] args) {

        // Opcion 1=CLiente  2=Productos  3=Pedidos
        String jsonCliente = new Controlador().recibirDatos(1);
        String jsonProducto = new Controlador().recibirDatos(2);
        String jsonPedido = new Controlador().recibirDatos(3);

        String jsonMaestro ="{\"clientes\": " + jsonCliente +
                ", \"productos\": " + jsonProducto +
                ", \"pedidos\": " + jsonPedido + "}";

        new ConexionServlet().enviarDatosAServidor(jsonMaestro);

    }
}