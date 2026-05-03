package com.politecnicomalaga.model;

public class Linea_pedido {
    private int id_linea;
    private int id_pedido;
    private int id_producto;
    private int cantidad;
    private float precio_unitario;
    private float subtotal;
    private Producto miProducto;

    public Linea_pedido(int id_linea, int id_pedido, int id_producto, int cantidad, float precio_unitario, Producto miProducto) {
        this.id_linea = id_linea;
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        setSubtotal(cantidad, precio_unitario);
        this.miProducto = miProducto;
    }

    public int getId_linea() {
        return id_linea;
    }

    public void setId_linea(int id_linea) {
        this.id_linea = id_linea;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int cantidad, float precio_unitario) {
        this.subtotal = cantidad * precio_unitario;
    }

    public Producto getMiProducto() {
        return miProducto;
    }

    public void setMiProducto(Producto miProducto) {
        this.miProducto = miProducto;
    }
}
