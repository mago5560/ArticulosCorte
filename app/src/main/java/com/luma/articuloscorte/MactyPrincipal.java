package com.luma.articuloscorte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.luma.articuloscorte.adapter.EmpleadosAdapter;
import com.luma.articuloscorte.adapter.EntregaArticuloAdapter;
import com.luma.articuloscorte.controller.ArticuloController;
import com.luma.articuloscorte.controller.EmpleadosController;
import com.luma.articuloscorte.controller.EntregaArticuloController;
import com.luma.articuloscorte.controller.UsuarioController;
import com.luma.articuloscorte.customs.Fuction;
import com.luma.articuloscorte.model.EntregaArticuloModel;

import java.util.ArrayList;

public class MactyPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
                    EntregaArticuloAdapter.OnItemClickListener{

    private Intent intent;
    Fuction util;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EntregaArticuloAdapter entregaArticuloAdapter;
    EntregaArticuloController entregaArticuloController;
    ArrayList<EntregaArticuloModel> entregaArticuloModelArrayList;

    UsuarioController usuarioController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.macty_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewsByIds();
        actions();
        buscar();
    }

    private void findViewsByIds() {
        this.util = new Fuction();
        this.usuarioController = new UsuarioController(this);

        //DrawerLayout
        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawerLayout.setDrawerListener(toggle);
        this.toggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.drawerLayout.openDrawer(GravityCompat.START, true);

        //RecyclerView
        swipeRefreshLayout=  findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.grdDatos);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        ((TextView) findViewById(R.id.lblTituloNavBar)).setText("Articulos Entregados");

    }


    private void actions(){


        ((ImageView) findViewById(R.id.imgvwMenu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionMenuCloseOpen();
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscar();
            }
        });

        ((Button) findViewById(R.id.btnAgregar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaEntrega();
            }
        });
    }

    private  void buscar(){
        entregaArticuloModelArrayList = new ArrayList<>();
        entregaArticuloAdapter = new EntregaArticuloAdapter(entregaArticuloModelArrayList, this, this);
        entregaArticuloController = new EntregaArticuloController(this
                , recyclerView
                , entregaArticuloAdapter
                , swipeRefreshLayout);
        entregaArticuloController.buscar();
        ((TextView) findViewById(R.id.lblFechaActualizacion)).setText(util.getFechaHoraActual());
    }

    @Override
    public void onClick(EntregaArticuloAdapter.ItemAdapterViewHolder holder, int position) {

    }

    @Override
    public void onTransferirClick(EntregaArticuloAdapter.ItemAdapterViewHolder holder, int position) {
        EntregaArticuloModel cls = entregaArticuloAdapter.info.get(position);
        entregaArticuloModelArrayList = new ArrayList<>();
        entregaArticuloAdapter = new EntregaArticuloAdapter(entregaArticuloModelArrayList, this, this);
        entregaArticuloController = new EntregaArticuloController(this
                , recyclerView
                , entregaArticuloAdapter
                , swipeRefreshLayout);
        entregaArticuloController.transferir(cls);
        ((TextView) findViewById(R.id.lblFechaActualizacion)).setText(util.getFechaHoraActual());
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_agregar: //TODO: NUEVA ENTREGA
                nuevaEntrega();
                break;
            case R.id.mnu_personal: //TODO: MAESTRO PERSONAL
                    descargarPersonal();
                break;
            case R.id.mnu_personal_demo: //TODO: MAESTRO PERSONAL
                descargarPersonalDemo();
                break;
            case R.id.mnu_articulo://TODO: MAESTRO ARTICULO
                    descargarArticulos();
                break;
            case R.id.mnu_salir://TODO: salir
                usuarioController.setLogout();
                break;
            default:
        }
        actionMenuCloseOpen();
        return false;
    }

    private void actionMenuCloseOpen(){
        if( drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START,true);
        }else{
            drawerLayout.openDrawer(GravityCompat.START,true);
        }
    }

    private void descargarPersonal(){
        EmpleadosController empleadosController = new EmpleadosController(this);
        empleadosController.getEmpleados();
    }

    private void descargarPersonalDemo(){
        EmpleadosController empleadosController = new EmpleadosController(this);
        empleadosController.getEmpleadosDemo();
    }

    private  void descargarArticulos(){
        ArticuloController articuloController = new ArticuloController(this);
        articuloController.getArticulos();
    }

    private void nuevaEntrega(){
        intent = new Intent().setClass(this,MactyEntregaArticulo.class);
        startActivity(intent);
        finish();
    }
    // <editor-fold defaultstate="collapsed" desc="regresar a principal">
    /*
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
     */
    // </editor-fold>

}