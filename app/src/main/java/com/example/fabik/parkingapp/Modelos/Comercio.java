package com.example.fabik.parkingapp.Modelos;

public class Comercio {

    private int id;
    private String name;
    private String documento;
    private String direccion;
    private String ciudad;
    private String estado;
    private String pais;
    private String telefono1;
    private String telefono2;
    private String header1;
    private String header2;
    private String footing1;
    private String footing2;
    private String moneda;
    private String simboloMoneda;
    private boolean centavos;

    public Comercio() {
    }

    public Comercio(int id, String name, String documento, String direccion, String ciudad, String estado, String pais, String telefono1, String telefono2, String header1, String header2, String footing1, String footing2, String moneda, String simboloMoneda, boolean centavos) {
        this.id = id;
        this.name = name;
        this.documento = documento;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.pais = pais;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.header1 = header1;
        this.header2 = header2;
        this.footing1 = footing1;
        this.footing2 = footing2;
        this.moneda = moneda;
        this.simboloMoneda = simboloMoneda;
        this.centavos = centavos;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getHeader1() {
        return header1;
    }

    public void setHeader1(String header1) {
        this.header1 = header1;
    }

    public String getHeader2() {
        return header2;
    }

    public void setHeader2(String header2) {
        this.header2 = header2;
    }

    public String getFooting1() {
        return footing1;
    }

    public void setFooting1(String footing1) {
        this.footing1 = footing1;
    }

    public String getFooting2() {
        return footing2;
    }

    public void setFooting2(String footing2) {
        this.footing2 = footing2;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getSimboloMoneda() {
        return simboloMoneda;
    }

    public void setSimboloMoneda(String simboloMoneda) {
        this.simboloMoneda = simboloMoneda;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public boolean isCentavos() {
        return centavos;
    }

    public void setCentavos(boolean centavos) {
        this.centavos = centavos;
    }
}
