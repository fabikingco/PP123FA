package com.example.fabik.parkingapp.Entidades;

import java.io.Serializable;

public class Ingresados implements Serializable{
    private Integer id;
    private String placa;
    private String fecha_ing;
    private String tipo;

    public Ingresados(Integer id, String placa, String fecha_ing, String tipo) {
        this.id = id;
        this.placa = placa;
        this.fecha_ing = fecha_ing;
        this.tipo = tipo;
    }

    public Ingresados() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFecha_ing() {
        return fecha_ing;
    }

    public void setFecha_ing(String fecha_ing) {
        this.fecha_ing = fecha_ing;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
