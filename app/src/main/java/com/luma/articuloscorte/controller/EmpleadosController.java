package com.luma.articuloscorte.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.luma.articuloscorte.adapter.EmpleadosAdapter;
import com.luma.articuloscorte.customs.DBHandler;
import com.luma.articuloscorte.customs.Fuction;
import com.luma.articuloscorte.customs.Global;
import com.luma.articuloscorte.model.ArticuloModel;
import com.luma.articuloscorte.model.EmpleadosModel;
import com.luma.articuloscorte.service.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpleadosController {
    Fuction util;
    Global var = Global.getInstance();
    Context context;
    DBHandler dbHandler;
    ApiService apiService;

    RecyclerView recyclerView;
    EmpleadosAdapter empleadosAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public EmpleadosController(Context context) {
        this.context = context;
        this.util = new Fuction();
        this.dbHandler = new DBHandler(context, null, null, 1);
    }

    public EmpleadosController(Context context, RecyclerView recyclerView, EmpleadosAdapter empleadosAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.empleadosAdapter = empleadosAdapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.util = new Fuction();
        this.dbHandler = new DBHandler(context, null, null, 1);
    }

    public void getEmpleadosDemo(){
        ProgressDialog pDialog;
        pDialog = ProgressDialog.show(context, "Descargando datos", "Espere....", true, false);
        pDialog.setTitle("Guardando Datos");
        pDialog.setMessage("Espere...");
        dbHandler.deletePersonal();
        EmpleadosModel cls = new EmpleadosModel();
        cls.setCODIGO(2002);
        cls.setNOMBRE("Luis Armando");
        cls.setApellido("Lima");
        cls.setDEPARTAMENTO(402);
        dbHandler.insertPersonal(cls);

        cls = new EmpleadosModel();
        cls.setCODIGO(2622);
        cls.setNOMBRE("Julio");
        cls.setApellido("Mejia Barrera");
        cls.setDEPARTAMENTO(402);
        dbHandler.insertPersonal(cls);

        cls = new EmpleadosModel();
        cls.setCODIGO(3113);
        cls.setNOMBRE("Hugo Oswaldo");
        cls.setApellido("Pineda Velasquez");
        cls.setDEPARTAMENTO(402);
        dbHandler.insertPersonal(cls);

        util.msgSnackBar("Descarga correctamente",context);

        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
    public void getEmpleados() {
        ProgressDialog pDialog;
        pDialog = ProgressDialog.show(context, "Descargando datos", "Espere....", true, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(var.getURL_API_EMPLEADOS())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<ArrayList<EmpleadosModel>> call = apiService.getPersonal();
        call.enqueue(new Callback<ArrayList<EmpleadosModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EmpleadosModel>> call, Response<ArrayList<EmpleadosModel>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                        dbHandler.deletePersonal();
                    for (EmpleadosModel cls: response.body()) {
                        dbHandler.insertPersonal(cls);
                    }
                    util.msgSnackBar("Descarga correctamente",context);
                } else {
                    util.mensaje("Ocurrio un problema al obtener los datos, verifique su conexion a internet y vuelva a intentar", ((Activity) context)).show();
                }
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EmpleadosModel>> call, Throwable t) {
                util.mensajeError(t.getMessage() + "\nVuelva a intentar", ((Activity) context)).show();
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }
        });
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
                return dbHandler.selectPersonal();
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
        empleadosAdapter.setInfo(arrayList);
        recyclerView.setAdapter(empleadosAdapter);
        swipeRefreshLayout.setRefreshing(false);
        if (arrayList.isEmpty()) {
            util.mensaje("No se encontraron registros, favor de descargar o ingresar la infromacion", ((Activity) context)).show();
        }
    }


}
