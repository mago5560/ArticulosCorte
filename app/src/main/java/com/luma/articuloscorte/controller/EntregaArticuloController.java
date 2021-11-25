package com.luma.articuloscorte.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.luma.articuloscorte.MactyPrincipal;
import com.luma.articuloscorte.adapter.EntregaArticuloAdapter;
import com.luma.articuloscorte.customs.DBHandler;
import com.luma.articuloscorte.customs.Fuction;
import com.luma.articuloscorte.customs.Global;
import com.luma.articuloscorte.model.EmpleadosModel;
import com.luma.articuloscorte.model.EntregaArticuloModel;
import com.luma.articuloscorte.service.ApiService;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EntregaArticuloController {

    Fuction util;
    Global var = Global.getInstance();
    Context context;
    DBHandler dbHandler;
    ApiService apiService;
    Intent intent;

    RecyclerView recyclerView;
    EntregaArticuloAdapter entregaArticuloAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public EntregaArticuloController(Context context) {
        this.context = context;
        this.util = new Fuction();
        this.dbHandler = new DBHandler(context, null, null, 1);
    }


    public EntregaArticuloController(Context context, RecyclerView recyclerView, EntregaArticuloAdapter entregaArticuloAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.entregaArticuloAdapter = entregaArticuloAdapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.util = new Fuction();
        this.dbHandler = new DBHandler(context, null, null, 1);
    }

    public void grabar(EntregaArticuloModel cls){

        ProgressDialog pDialog;
        pDialog = ProgressDialog.show(context, "Enviando datos", "Espere....", true, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(var.getURL_API_ENTREGA_ARTICULO())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> call = apiService.setEnregaArticulo(cls);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dbHandler.insertEntregaArticulo(cls);
                if (response.isSuccessful() && response.code() == 200) {
                    dbHandler.updateEntregaArticuloTransferido(cls.getID());
                    util.msgSnackBar("Se enviaron los datos correctamente",context);
                } else {
                    util.msgSnackBar("Ocurrio un problema al enviar los datos, verifique su conexion a internet y vuelva a intentar", ((Activity) context));
                }
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                mactyPrincipal();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                dbHandler.insertEntregaArticulo(cls);
                mactyPrincipal();
                util.msgSnackBar("Ocurrio un problema, se graba localmente la informacion",context);
            }
        });

    }

    private void mactyPrincipal() {
        intent = new Intent().setClass(context, MactyPrincipal.class);
        ((Activity) context).startActivity(intent);
        ((Activity) context).finish();
    }

    AsyncBuscar asyncBuscar;
    public void buscar() {
        recyclerView.setScrollingTouchSlop(0);
        asyncBuscar = new AsyncBuscar();
        asyncBuscar.execute();
    }

    private class AsyncBuscar extends AsyncTask<String, Void, ArrayList> {
        private ProgressDialog pDialog;

        @Override
        protected ArrayList doInBackground(String... strings) {
            return dbHandler.selectEntregaArticulo();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(context, "Consultando...", "Espere....", true, true);
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            viewConsulta(arrayList);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }

    private void viewConsulta(ArrayList arrayList) {
        entregaArticuloAdapter.setInfo(arrayList);
        recyclerView.setAdapter(entregaArticuloAdapter);
        swipeRefreshLayout.setRefreshing(false);
        if (arrayList.isEmpty()) {
           util.msgSnackBar("No se encontraron registros",context);
        }
    }




    public void transferir(EntregaArticuloModel cls){

        ProgressDialog pDialog;
        pDialog = ProgressDialog.show(context, "Enviando datos", "Espere....", true, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(var.getURL_API_ENTREGA_ARTICULO())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> call = apiService.setEnregaArticulo(cls);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    dbHandler.updateEntregaArticuloTransferido(cls.getID());
                    util.msgSnackBar("Se enviaron los datos correctamente",context);
                    buscar();
                } else {
                    util.msgSnackBar("Ocurrio un problema al enviar los datos, verifique su conexion a internet y vuelva a intentar", ((Activity) context));
                }
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                util.mensajeError(t.getMessage() + "\nVuelva a intentar", ((Activity) context)).show();
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

            }
        });

    }


}
