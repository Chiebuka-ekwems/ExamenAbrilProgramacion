package com.politecnicomalaga.tienda.view;

import com.politecnicomalaga.tienda.controller.Controlador;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BuscarProductoxCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String codigo=request.getParameter("id_producto");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if(codigo != null && !codigo.isEmpty()){
            out.println((new Controlador()).findProductoXCodigo(codigo));
        }else{
            out.println("{\"error\": \"Debe proporcionar un codigo de producto\"}");
        }

    }
}
