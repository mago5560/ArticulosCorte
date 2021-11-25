package com.luma.articuloscorte.model;

public class ArticuloModel {

    private int CODIGOARTICULO=0;
    private String ARTICULODESCRIPCION="";
    private int CANTIDAD=0;

    public int getCODIGOARTICULO() {
        return CODIGOARTICULO;
    }

    public void setCODIGOARTICULO(int CODIGOARTICULO) {
        this.CODIGOARTICULO = CODIGOARTICULO;
    }

    public String getARTICULODESCRIPCION() {
        return ARTICULODESCRIPCION;
    }

    public void setARTICULODESCRIPCION(String ARTICULODESCRIPCION) {
        this.ARTICULODESCRIPCION = ARTICULODESCRIPCION;
    }

    public int getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(int CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    @Override
    public String toString() {
        return  CODIGOARTICULO +
                " " + ARTICULODESCRIPCION ;
    }
}
