package com.politecnicomalaga.tienda.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String direccion;
    private List<Pedido> misPedidos;

    public Cliente(String dni,String nombre, String apellidos, String email, String telefono, String direccion){
        this.dni=dni;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.email=email;
        this.telefono=telefono;
        this.direccion=direccion;
        this.misPedidos= new ArrayList<>();
    }

    public List<Pedido> getMisPedidos() {
        return misPedidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
