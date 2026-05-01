package com.politecnicomalaga.tienda.controller;



import com.google.gson.Gson;
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



}