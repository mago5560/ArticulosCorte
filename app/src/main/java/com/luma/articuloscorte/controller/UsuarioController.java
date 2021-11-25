package com.luma.articuloscorte.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.luma.articuloscorte.MactyLogin;
import com.luma.articuloscorte.MactyPrincipal;
import com.luma.articuloscorte.customs.Fuction;
import com.luma.articuloscorte.model.UsuarioModel;

public class UsuarioController {

    UsuarioModel usuarioClass;
    Context context;

    Intent intent;
    //ApiService apiService;
    //GlobalCustoms var = GlobalCustoms.getInstance();
    Fuction util;

    private SharedPreferences sharedPreferences;
    private static final String Preferences = "usuario";

    public UsuarioController(Context context) {
        this.context = context;
        this.usuarioClass = new UsuarioModel();
        sharedPreferences = this.context.getSharedPreferences(Preferences, Context.MODE_PRIVATE);
    }


    public void serviceLogin(String Usuario, String Password, Boolean Recordar) {
        setLogin(Usuario, Password, Recordar);
        mactyPrincipal();
    }


    public UsuarioModel getUsuario() {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setUSUARIO(sharedPreferences.getString("usuario", ""));
        usuarioModel.setPASSWORD(sharedPreferences.getString("password", ""));
        usuarioModel.setRECORDAR(sharedPreferences.getBoolean("recordar", false));
        return usuarioModel;
    }

    public void setLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", "");
        editor.putString("password", "");
        editor.putBoolean("recordar", false);
        editor.commit();
        mactyLogin();
    }

    public void setLogin(String Usuario, String Password, Boolean Recordar) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", Usuario);
        editor.putString("password", Password);
        editor.putBoolean("recordar", Recordar);
        editor.commit();
    }

    public void isLogin() {
        if ( getUsuario().isRECORDAR()) {
            mactyPrincipal();
        }
    }

    private void mactyPrincipal() {
        intent = new Intent().setClass(context, MactyPrincipal.class);
        ((Activity) context).startActivity(intent);
        ((Activity) context).finish();
    }

    private void mactyLogin() {
        intent = new Intent().setClass(context, MactyLogin.class);
        ((Activity) context).startActivity(intent);
        ((Activity) context).finish();
    }

}
