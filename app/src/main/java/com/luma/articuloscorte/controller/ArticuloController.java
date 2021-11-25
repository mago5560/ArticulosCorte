package com.luma.articuloscorte.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.luma.articuloscorte.adapter.ArticuloAdapter;
import com.luma.articuloscorte.adapter.EmpleadosAdapter;
import com.luma.articuloscorte.customs.DBHandler;
import com.luma.articuloscorte.customs.Fuction;
import com.luma.articuloscorte.customs.Global;
import com.luma.articuloscorte.model.ArticuloModel;
import com.luma.articuloscorte.service.ApiService;

import java.util.ArrayList;

public class ArticuloController {
    Fuction util;
    Global var = Global.getInstance();
    Context context;
    DBHandler dbHandler;
    ApiService apiService;


    RecyclerView recyclerView;
    ArticuloAdapter articuloAdapter;
    SwipeRefreshLayout swipeRefreshLayout;


    public ArticuloController(Context context) {
        this.context = context;
        this.util = new Fuction();
        this.dbHandler = new DBHandler(context, null, null, 1);
    }


    public ArticuloController(Context context, RecyclerView recyclerView, ArticuloAdapter articuloAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.articuloAdapter = articuloAdapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.util = new Fuction();
        this.dbHandler = new DBHandler(context, null, null, 1);
    }

    public void getArticulos() {
        ProgressDialog pDialog;
        pDialog = ProgressDialog.show(context, "Descargando datos", "Espere....", true, false);
        pDialog.setTitle("Guardando Datos");
        pDialog.setMessage("Espere...");
        dbHandler.deleteArticulo();
        ArticuloModel cls = new ArticuloModel();
        cls.setCANTIDAD(10);
        cls.setCODIGOARTICULO(1);
        cls.setARTICULODESCRIPCION("Lima");
        dbHandler.insertArticulo(cls);

        cls = new ArticuloModel();
        cls.setCANTIDAD(10);
        cls.setCODIGOARTICULO(2);
        cls.setARTICULODESCRIPCION("Termo");
        dbHandler.insertArticulo(cls);

        cls = new ArticuloModel();
        cls.setCANTIDAD(10);
        cls.setCODIGOARTICULO(3);
        cls.setARTICULODESCRIPCION("Colchoneta");
        dbHandler.insertArticulo(cls);

        cls = new ArticuloModel();
        cls.setCANTIDAD(10);
        cls.setCODIGOARTICULO(4);
        cls.setARTICULODESCRIPCION("Utensilios");
        dbHandler.insertArticulo(cls);

        util.msgSnackBar("Descarga correctamente",context);

        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
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
            return dbHandler.selectArticulo();
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
        articuloAdapter.setInfo(arrayList);
        recyclerView.setAdapter(articuloAdapter);
        swipeRefreshLayout.setRefreshing(false);
        if (arrayList.isEmpty()) {
            util.mensaje("No se encontraron registros, favor de descargar o ingresar la infromacion", ((Activity) context)).show();
        }
    }

}
