package com.politecnicomalaga.tienda.view;

import com.politecnicomalaga.tienda.controller.Controlador;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ListarProductosxPedidos extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String codigo=request.getParameter("id_pedido");
        String dni=request.getParameter("dni");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        out.println((new Controlador()).listProductosXPedido(dni,codigo));
    }
}
