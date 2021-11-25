package com.luma.articuloscorte;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.luma.articuloscorte.adapter.ArticuloAdapter;
import com.luma.articuloscorte.adapter.EmpleadosAdapter;
import com.luma.articuloscorte.controller.ArticuloController;
import com.luma.articuloscorte.controller.EmpleadosController;
import com.luma.articuloscorte.controller.EntregaArticuloController;
import com.luma.articuloscorte.customs.Fuction;
import com.luma.articuloscorte.model.ArticuloModel;
import com.luma.articuloscorte.model.EmpleadosModel;
import com.luma.articuloscorte.model.EntregaArticuloModel;

import java.util.ArrayList;

public class MactyEntregaArticulo extends AppCompatActivity
        implements EmpleadosAdapter.OnItemClickListener,
                   ArticuloAdapter.OnItemClickListener {

    Intent intent;
    Fuction util;

    EmpleadosModel empleadosModel;
    ArticuloModel articuloModel;
    EntregaArticuloModel entregaArticuloModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.macty_entrega_articulo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewsByIds();
        actions();
    }

    private void findViewsByIds(){
        util = new Fuction();
        empleadosModel = new EmpleadosModel();
        articuloModel = new ArticuloModel();

        ((TextView) findViewById(R.id.lblFecha)).setText(util.getFechaActual());
    }
    private void actions(){
        ((ImageView) findViewById(R.id.imgvwRetroceder)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });

        ((TextView) findViewById(R.id.lblFecha)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.getFechaDialog(MactyEntregaArticulo.this,((TextView) view));
            }
        });

        ((Button) findViewById(R.id.btnEmpleado)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogProducto();
            }
        });

        ((Button) findViewById(R.id.btnArticulo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogArticulo();
            }
        });

        ((Button) findViewById(R.id.btnEntregar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grabar();
            }
        });
    }


    private void grabar(){
        if(validarCampos()){
            entregaArticuloModel = new EntregaArticuloModel();
            entregaArticuloModel.setRESPONSABLE(empleadosModel.getCODIGO());
            entregaArticuloModel.setFECHAENTREGA(((TextView) findViewById(R.id.lblFecha)).getText().toString());
            entregaArticuloModel.setCANTIDAD(Integer.valueOf(((EditText) findViewById(R.id.txtCantidad)).getText().toString()));
            entregaArticuloModel.setEMPLEADO(empleadosModel.getCODIGO());
            entregaArticuloModel.setCODIGOARTICULO(articuloModel.getCODIGOARTICULO());
            entregaArticuloModel.setARTICULODESCRIPCION(articuloModel.getARTICULODESCRIPCION());
            EntregaArticuloController entregaArticuloController = new EntregaArticuloController(this);
            entregaArticuloController.grabar(entregaArticuloModel);
        }
    }

    private boolean validarCampos(){

        if(util.validarCampoVacio( ((EditText) findViewById(R.id.txtCantidad))) ){
            return false;
        }
        if(empleadosModel.getCODIGO() == 0){
            util.mensaje("Debe de seleccionar un empleado",this).show();
            return false;
        }
        if(articuloModel.getCODIGOARTICULO() == 0){
            util.mensaje("Debe de seleccionar un articulo",this).show();
            return false;
        }
        return true;
    }

    AlertDialog alertDialogMaestro;

    // <editor-fold defaultstate="collapsed" desc="(Select Empleado)">

    EmpleadosAdapter empleadosAdapter;
    ArrayList<EmpleadosModel> empleadosModelArrayList;
    EmpleadosController empleadosController;

    private void dialogProducto() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        final LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.content_maestro, null);
        dialogProductofindViewsByIds(dialogLayout);
        dialogProductoactions(dialogLayout);
        buscarProducto(dialogLayout);
        builder.setTitle("Seleccione El Registro A Usar");

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(dialogLayout);
        alertDialogMaestro = builder.show();
    }

    private void dialogProductofindViewsByIds(View v) {
        //RecyclerView
        ((RecyclerView) v.findViewById(R.id.grdDatos)).setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        ((RecyclerView) v.findViewById(R.id.grdDatos)).setLayoutManager(llm);
        ((TextView) v.findViewById(R.id.lblTituloNavBar)).setText("");
    }


    private void dialogProductoactions(View v) {
        ((ImageView) v.findViewById(R.id.imgvwAbrirBuscador)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                visibleFilter(v, true);
            }
        });

        ((ImageView) v.findViewById(R.id.imgvwCerrarBuscador)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                visibleFilter(v, false);
            }
        });

        ((SearchView) v.findViewById(R.id.txtBuscador)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                empleadosAdapter.getFilter().filter(s);
                return false;
            }
        });

        ((SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh)).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscarProducto(v);
            }
        });

    }


    private void buscarProducto(View v) {
        empleadosModelArrayList = new ArrayList<>();
        empleadosAdapter = new EmpleadosAdapter(empleadosModelArrayList, this, this);
        empleadosController = new EmpleadosController(this
                , ((RecyclerView) v.findViewById(R.id.grdDatos))
                , empleadosAdapter
                , ((SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh)));
        empleadosController.buscar();
        ((TextView) v.findViewById(R.id.lblFechaActualizacion)).setText(util.getFechaHoraActual());
    }


    @Override
    public void onClick(EmpleadosAdapter.ItemAdapterViewHolder holder, int position) {
        empleadosModel = empleadosAdapter.info.get(position);
        ((TextView) findViewById(R.id.lblNombreEmpledo)).setText(empleadosModel.getNOMBRE()+" "+ empleadosModel.getApellido());
        alertDialogMaestro.dismiss();
    }

// </editor-fold>


    private void visibleFilter(View v, boolean visible) {
        if (visible) {
            ((LinearLayout) v.findViewById(R.id.llyFilter)).setVisibility(View.VISIBLE);
            ((SearchView) v.findViewById(R.id.txtBuscador)).requestFocus();
            ((ImageView) v.findViewById(R.id.imgvwAbrirBuscador)).setVisibility(View.GONE);
        } else {
            ((LinearLayout) v.findViewById(R.id.llyFilter)).setVisibility(View.GONE);
            ((SearchView) v.findViewById(R.id.txtBuscador)).setQuery("", false);
            ((ImageView) v.findViewById(R.id.imgvwAbrirBuscador)).setVisibility(View.VISIBLE);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="(Select Articulo)">
    ArticuloAdapter articuloAdapter;
    ArrayList<ArticuloModel> articuloModelArrayList;
    ArticuloController articuloController;

    private void dialogArticulo() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        final LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.content_maestro, null);
        dialogArticulofindViewsByIds(dialogLayout);
        dialogArticuloactions(dialogLayout);
        buscarArticulo(dialogLayout);
        builder.setTitle("Seleccione El Registro A Usar");

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(dialogLayout);
        alertDialogMaestro = builder.show();
    }

    private void dialogArticulofindViewsByIds(View v) {
        //RecyclerView
        ((RecyclerView) v.findViewById(R.id.grdDatos)).setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        ((RecyclerView) v.findViewById(R.id.grdDatos)).setLayoutManager(llm);
        ((TextView) v.findViewById(R.id.lblTituloNavBar)).setText("");
    }


    private void dialogArticuloactions(View v) {
        ((ImageView) v.findViewById(R.id.imgvwAbrirBuscador)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                visibleFilter(v, true);
            }
        });

        ((ImageView) v.findViewById(R.id.imgvwCerrarBuscador)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                visibleFilter(v, false);
            }
        });

        ((SearchView) v.findViewById(R.id.txtBuscador)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                articuloAdapter.getFilter().filter(s);
                return false;
            }
        });

        ((SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh)).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscarProducto(v);
            }
        });

    }


    private void buscarArticulo(View v) {
        articuloModelArrayList = new ArrayList<>();
        articuloAdapter = new ArticuloAdapter(articuloModelArrayList, this, this);
        articuloController = new ArticuloController(this
                , ((RecyclerView) v.findViewById(R.id.grdDatos))
                , articuloAdapter
                , ((SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh)));
        articuloController.buscar();
        ((TextView) v.findViewById(R.id.lblFechaActualizacion)).setText(util.getFechaHoraActual());
    }


    @Override
    public void onClick(ArticuloAdapter.ItemAdapterViewHolder holder, int position) {
        articuloModel = articuloAdapter.info.get(position);
        ((TextView) findViewById(R.id.lblDescripcionArticulo)).setText(articuloModel.getARTICULODESCRIPCION());
        alertDialogMaestro.dismiss();
    }

    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="regresar a principal">
    @Override
    public void onBackPressed() {
        goHome();
        super.onBackPressed();
    }

    private void goHome() {
        intent = new Intent().setClass(this, MactyPrincipal.class);
        startActivity(intent);
        finish();
    }

    // </editor-fold>

}