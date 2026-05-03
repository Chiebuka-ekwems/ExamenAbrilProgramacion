package com.politecnicomalaga.controller;
import  com.politecnicomalaga.model.*;

public class Controlador {
    public Controlador(){}

    public String leerFichero(String rutaFichero){
        return GestorArchivosTexto.leerDeFichero(rutaFichero);
    }

    public String recibirDatos(int opcion){
        String ruta ="/home/chiebuka/IdeaProjects/examen_progr_abril2026-main/data/data.csv";
        return GestorArchivosTexto.leerDeFichero1(ruta,opcion);
    }

}
