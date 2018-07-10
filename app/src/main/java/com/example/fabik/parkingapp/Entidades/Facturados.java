package com.example.fabik.parkingapp.Entidades;

import java.io.Serializable;

public class Facturados implements Serializable{
    private Integer id;
    private String nombre;
    private String documento;
    private String producto;
    private String valor;

    public Facturados(Integer id, String nombre, String documento, String producto, String valor) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.producto = producto;
        this.valor = valor;
    }

    public Facturados() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
