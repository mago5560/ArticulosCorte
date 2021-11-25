package com.luma.articuloscorte.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpleadosModel {

    @SerializedName("codigo")
    @Expose
    private int CODIGO=0;

    @SerializedName("nombre")
    @Expose
    private String NOMBRE ="";

    @SerializedName("apellido")
    @Expose
    private String Apellido="";

    @SerializedName("departamento")
    @Expose
    private int DEPARTAMENTO=0;

    public int getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(int CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public int getDEPARTAMENTO() {
        return DEPARTAMENTO;
    }

    public void setDEPARTAMENTO(int DEPARTAMENTO) {
        this.DEPARTAMENTO = DEPARTAMENTO;
    }

    @Override
    public String toString() {
        return  CODIGO +
                " " + NOMBRE +
                " " + Apellido ;
    }
}
