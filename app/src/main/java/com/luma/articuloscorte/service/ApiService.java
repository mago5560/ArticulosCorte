package com.luma.articuloscorte.service;

import com.luma.articuloscorte.model.ArticuloModel;
import com.luma.articuloscorte.model.EmpleadosModel;
import com.luma.articuloscorte.model.EntregaArticuloModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    String PERSONAL = "empleados";
    String ENTREGA_ARTICULO = "regModuloArt";

    @Headers({
            "Content-type: application/json"
    })
    @GET(PERSONAL)
    Call<ArrayList<EmpleadosModel>> getPersonal();


    @Headers({
            "Content-type: application/json"
    })
    @POST(ENTREGA_ARTICULO)
    Call<ResponseBody> setEnregaArticulo(@Body EntregaArticuloModel cls);
}
