package com.politecnicomalaga.tienda.model;

//Clase producto

public class Producto {
    private int id_producto;
    private String descripcion;
    private float precio_unitario;

    public Producto(int id_producto, String descripcion, float precio_unitario) {
        this.id_producto = id_producto;
        this.descripcion = descripcion;
        this.precio_unitario = precio_unitario;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
}
