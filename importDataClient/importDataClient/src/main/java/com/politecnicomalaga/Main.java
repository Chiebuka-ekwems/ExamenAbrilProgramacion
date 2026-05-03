package com.politecnicomalaga;

import com.politecnicomalaga.controller.Controlador;

public class Main {
    public static void main(String[] args) {

        // Opcion 1=CLiente  2=Productos  3=Pedidos
        String jsonCliente = new Controlador().recibirDatos(1);
        String jsonProducto = new Controlador().recibirDatos(2);
        String jsonPedido = new Controlador().recibirDatos(3);

    }
}