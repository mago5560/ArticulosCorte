package com.luma.articuloscorte.model;

public class UsuarioModel {

    private String USUARIO="";
    private String PASSWORD="";
    private boolean RECORDAR=false;

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public boolean isRECORDAR() {
        return RECORDAR;
    }

    public void setRECORDAR(boolean RECORDAR) {
        this.RECORDAR = RECORDAR;
    }
}
