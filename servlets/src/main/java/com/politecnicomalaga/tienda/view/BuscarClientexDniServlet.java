package com.politecnicomalaga.tienda.view;

import com.politecnicomalaga.tienda.controller.Controlador;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BuscarClientexDniServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dni=request.getParameter("dni");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if(dni != null && !dni.isEmpty()){
            out.println((new Controlador()).findClienteXDNI(dni));
        }else{
            out.println("{\"error\": \"Debe proporcionar un codigo de producto\"}");
        }

    }
}
