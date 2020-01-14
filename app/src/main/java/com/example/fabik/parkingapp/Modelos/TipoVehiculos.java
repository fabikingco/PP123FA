package com.example.fabik.parkingapp.Modelos;

public class TipoVehiculos {

    private int id;
    private String name;
    private int img;
    private String modo;
    private String precio;

    public TipoVehiculos() {
    }

    public TipoVehiculos(int id, String name, int img, String modo, String precio) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.modo = modo;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
