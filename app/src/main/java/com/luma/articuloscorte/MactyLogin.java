package com.luma.articuloscorte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.luma.articuloscorte.controller.UsuarioController;
import com.luma.articuloscorte.customs.Fuction;

public class MactyLogin extends AppCompatActivity {
    UsuarioController usuarioController;
    Fuction util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.macty_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    findViewsById();
    actions();
}

    private void findViewsById(){
        util = new Fuction();
        usuarioController = new UsuarioController(this);
        usuarioController.isLogin();
        ((TextView) findViewById(R.id.lblVersion)).setText(util.getVersion(this));
    }

    private void actions(){
        ((Button) findViewById(R.id.btnIngreso)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });
    }

    private void iniciarSesion(){
        if(validarCampos()){
            usuarioController.serviceLogin(
                    ((EditText) findViewById(R.id.txtUsuario)).getText().toString() ,
                    ((EditText) findViewById(R.id.txtContraseña)).getText().toString(),
                    ((CheckBox) findViewById(R.id.chkRecordadIngreso)).isChecked()
            );
        }
    }
    private boolean validarCampos(){
        if(util.validarCampoVacio(((EditText) findViewById(R.id.txtUsuario)))){
            return false;
        }
        if(util.validarCampoVacio(((EditText) findViewById(R.id.txtContraseña)))){
            return false;
        }

        return true;
    }

}