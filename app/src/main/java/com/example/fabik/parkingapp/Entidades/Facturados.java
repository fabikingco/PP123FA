package com.example.fabik.parkingapp.Entidades;

import java.io.Serializable;

public class Facturados implements Serializable{
    private Integer id;
    private String placa;
    private String fecha_ing;
    private String fecha_sal;
    private String tipo;
    private Integer valor;
    private Integer minutos;

    public Facturados(Integer id, String placa, String fecha_ing, String fecha_sal, String tipo, Integer valor, Integer minutos) {
        this.id = id;
        this.placa = placa;
        this.fecha_ing = fecha_ing;
        this.fecha_sal = fecha_sal;
        this.tipo = tipo;
        this.valor = valor;
        this.minutos = minutos;
    }

    public Facturados(){

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

    public String getFecha_sal() {
        return fecha_sal;
    }

    public void setFecha_sal(String fecha_sal) {
        this.fecha_sal = fecha_sal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }
}
