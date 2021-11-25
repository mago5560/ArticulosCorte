package com.luma.articuloscorte.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntregaArticuloModel {

    private int ID=0;

    @SerializedName("articulo")
    @Expose
    private String ARTICULODESCRIPCION="";

    @SerializedName("codigo")
    @Expose
    private int CODIGOARTICULO=0;

    @SerializedName("cantidad")
    @Expose
    private int CANTIDAD=0;

    @SerializedName("empleado")
    @Expose
    private int EMPLEADO=0;

    private String NOMBREEMPLEADO="";

    @SerializedName("fecha")
    @Expose
    private String FECHAENTREGA="";

    @SerializedName("id")
    @Expose
    private int CODIGOINTERNO=0;

    @SerializedName("responsable")
    @Expose
    private int RESPONSABLE=0;

    private int TRANSFERIDO = 0;

    public String getARTICULODESCRIPCION() {
        return ARTICULODESCRIPCION;
    }

    public void setARTICULODESCRIPCION(String ARTICULODESCRIPCION) {
        this.ARTICULODESCRIPCION = ARTICULODESCRIPCION;
    }

    public int getCODIGOARTICULO() {
        return CODIGOARTICULO;
    }

    public void setCODIGOARTICULO(int CODIGOARTICULO) {
        this.CODIGOARTICULO = CODIGOARTICULO;
    }

    public int getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(int CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public int getEMPLEADO() {
        return EMPLEADO;
    }

    public void setEMPLEADO(int EMPLEADO) {
        this.EMPLEADO = EMPLEADO;
    }

    public String getNOMBREEMPLEADO() {
        return NOMBREEMPLEADO;
    }

    public void setNOMBREEMPLEADO(String NOMBREEMPLEADO) {
        this.NOMBREEMPLEADO = NOMBREEMPLEADO;
    }

    public String getFECHAENTREGA() {
        return FECHAENTREGA;
    }

    public void setFECHAENTREGA(String FECHAENTREGA) {
        this.FECHAENTREGA = FECHAENTREGA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRESPONSABLE() {
        return RESPONSABLE;
    }

    public void setRESPONSABLE(int RESPONSABLE) {
        this.RESPONSABLE = RESPONSABLE;
    }

    public int getCODIGOINTERNO() {
        return CODIGOINTERNO;
    }

    public void setCODIGOINTERNO(int CODIGOINTERNO) {
        this.CODIGOINTERNO = CODIGOINTERNO;
    }

    public int getTRANSFERIDO() {
        return TRANSFERIDO;
    }

    public void setTRANSFERIDO(int TRANSFERIDO) {
        this.TRANSFERIDO = TRANSFERIDO;
    }
}
