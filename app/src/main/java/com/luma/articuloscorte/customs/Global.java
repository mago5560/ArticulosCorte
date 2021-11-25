package com.luma.articuloscorte.customs;

public class Global {

    private static Global instance;

    public Global() {
    }

    public static  synchronized  Global getInstance(){
        if(instance == null){
            instance = new Global();
        }
        return instance;
    }

    private String URL_API="";
    private String URL_API_EMPLEADOS="http://10.10.2.7:8080/cosechamecanizada/api/";
    private String URL_API_ENTREGA_ARTICULO="http://10.10.2.7:8080/WSReunion5/reuniones/";

    private String INFO_FILE="";
    private String LINK_APP="";

    private String NAME_APP="Articulos Corte";
    private String PATH_FILE_APP="/ArticulosCorte";

    public String getURL_API() {
        return URL_API;
    }

    public String getINFO_FILE() {
        return INFO_FILE;
    }

    public String getLINK_APP() {
        return LINK_APP;
    }

    public String getNAME_APP() {
        return NAME_APP;
    }

    public String getPATH_FILE_APP() {
        return PATH_FILE_APP;
    }

    public String getURL_API_EMPLEADOS() {
        return URL_API_EMPLEADOS;
    }

    public String getURL_API_ENTREGA_ARTICULO() {
        return URL_API_ENTREGA_ARTICULO;
    }
}
