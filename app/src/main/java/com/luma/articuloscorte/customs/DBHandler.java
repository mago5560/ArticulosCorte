package com.luma.articuloscorte.customs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.luma.articuloscorte.model.ArticuloModel;
import com.luma.articuloscorte.model.EmpleadosModel;
import com.luma.articuloscorte.model.EntregaArticuloModel;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DbVersion = 1;
    private static final String DbName = "santaana.db";
    private String query;
    private SQLiteDatabase db;

    private static final String tblPersonal = "tblpersonal";
    private static final String tblArticulo = "tblarticulo";
    private static final String tblEntregaArticulo = "tblEntregaArticulo";


    public String getDbVersion() {
        return "Version de BD: " + DbVersion;
    }


    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DbName, factory, DbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
        createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.db = sqLiteDatabase;
        updateTable();
    }

    // <editor-fold defaultstate="collapsed" desc="(Create Table)">
    private void createTable() {
        db.execSQL(" DROP TABLE IF EXISTS " + tblPersonal);
        query = "";
        query = " CREATE TABLE " + tblPersonal + " ( ";
        query += " Id INTEGER PRIMARY KEY AUTOINCREMENT ";
        query += " ,codigo INTEGER  ";
        query += " ,nombre TEXT ";
        query += " ,apellido TEXT ";
        query += " ,departamento INTEGER";
        query += " ) ";
        db.execSQL(query);

        db.execSQL(" DROP TABLE IF EXISTS " + tblArticulo);
        query = "";
        query = " CREATE TABLE " + tblArticulo + " ( ";
        query += " Id INTEGER PRIMARY KEY AUTOINCREMENT ";
        query += " ,codigoArticulo INTEGER ";
        query += " ,descripcion TEXT ";
        query += " ,cantidad INTEGER DEFAULT 0";
        query += " ) ";
        db.execSQL(query);


        db.execSQL(" DROP TABLE IF EXISTS " + tblEntregaArticulo);
        query = "";
        query = " CREATE TABLE " + tblEntregaArticulo + " ( ";
        query += " Id INTEGER PRIMARY KEY AUTOINCREMENT ";
        query += " ,articulodescripcion TEXT ";
        query += " ,codigoarticulo INTEGER ";
        query += " ,cantidad INTEGER";
        query += " ,empleado INTEGER";
        query += " ,fechaentrega TEXT";
        query += " ,responsable INTEGER ";
        query += " ,Transferido INTEGER DEFAULT 0";
        query += " ) ";
        db.execSQL(query);

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="(Update Table)">
    private void updateTable() {

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="(insert)">
    public int insertPersonal(EmpleadosModel cls) {
        long id = 0;
        this.db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", cls.getCODIGO());
        contentValues.put("nombre", cls.getNOMBRE());
        contentValues.put("apellido", cls.getApellido());
        contentValues.put("departamento", cls.getDEPARTAMENTO());

        id = this.db.insert(tblPersonal, null, contentValues);
        this.db.close();
        return (int) id;
    }

    public int insertArticulo(ArticuloModel cls) {
        long id = 0;
        this.db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("codigoArticulo", cls.getCODIGOARTICULO());
        contentValues.put("descripcion", cls.getARTICULODESCRIPCION());
        contentValues.put("cantidad", cls.getCANTIDAD());

        id = this.db.insert(tblArticulo, null, contentValues);
        this.db.close();
        return (int) id;
    }

    public int insertEntregaArticulo(EntregaArticuloModel cls) {
        long id = 0;
        this.db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("articulodescripcion", cls.getARTICULODESCRIPCION());
        contentValues.put("codigoarticulo", cls.getCODIGOARTICULO());
        contentValues.put("cantidad", cls.getCANTIDAD());
        contentValues.put("empleado", cls.getEMPLEADO());
        contentValues.put("fechaentrega", cls.getFECHAENTREGA());
        contentValues.put("responsable", cls.getRESPONSABLE());

        id = this.db.insert(tblEntregaArticulo, null, contentValues);
        this.db.close();
        return (int) id;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="(update)">

    public void updateEntregaArticulo(EntregaArticuloModel cls) {
        this.db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("articulodescripcion", cls.getCODIGOARTICULO());
        contentValues.put("codigoarticulo", cls.getARTICULODESCRIPCION());
        contentValues.put("cantidad", cls.getCANTIDAD());
        contentValues.put("empleado", cls.getEMPLEADO());
        contentValues.put("fechaentrega", cls.getFECHAENTREGA());
        contentValues.put("responsable", cls.getRESPONSABLE());
        this.db.update(tblEntregaArticulo, contentValues, "Id = " + cls.getID(), null);
        this.db.close();
    }

    public void updateEntregaArticuloTransferido(int Id) {
        this.db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Transferido", "1");
        this.db.update(tblEntregaArticulo, contentValues, "Id = " + Id, null);
        this.db.close();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="(delete)">
    public void deleteArticulo() {
        this.db = getWritableDatabase();
        this.db.delete(tblArticulo, null, null);
        this.db.close();
    }

    public void deletePersonal() {
        this.db = getWritableDatabase();
        this.db.delete(tblPersonal, null, null);
        this.db.close();
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="(select)">

    public ArrayList selectArticulo() {
        ArrayList<ArticuloModel> list = new ArrayList<>();
        this.db = getWritableDatabase();
        query = "";
        query += "SELECT ";
        query += " Id ";
        query += " ,codigoArticulo   ";
        query += " ,descripcion  ";
        query += " ,cantidad   ";
        query += " FROM " + tblArticulo;
        query += "  ORDER BY codigoArticulo ASC ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            ArticuloModel cls = new ArticuloModel();
            cls.setCODIGOARTICULO(c.getInt(c.getColumnIndex("codigoArticulo")));
            cls.setARTICULODESCRIPCION(c.getString(c.getColumnIndex("descripcion")));
            cls.setCANTIDAD(c.getInt(c.getColumnIndex("cantidad")));
            list.add(cls);
            c.moveToNext();
        }
        this.db.close();
        return list;
    }

    public ArticuloModel selectArticulo(int Id) {
        ArticuloModel cls = new ArticuloModel();
        this.db = getWritableDatabase();
        query = "";
        query += "SELECT ";
        query += " Id ";
        query += " ,codigoArticulo   ";
        query += " ,descripcion  ";
        query += " ,cantidad   ";
        query += " FROM " + tblArticulo;
        query += " WHERE codigoArticulo = " + Id;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {

            cls.setCODIGOARTICULO(c.getInt(c.getColumnIndex("codigoArticulo")));
            cls.setARTICULODESCRIPCION(c.getString(c.getColumnIndex("descripcion")));
            cls.setCANTIDAD(c.getInt(c.getColumnIndex("cantidad")));
            c.moveToNext();
        }
        this.db.close();
        return cls;
    }

    public ArrayList selectPersonal() {
        ArrayList<EmpleadosModel> list = new ArrayList<>();
        this.db = getWritableDatabase();

        query = "";
        query += "SELECT ";
        query += " Id ";
        query += " ,codigo    ";
        query += " ,nombre   ";
        query += " ,apellido   ";
        query += " ,departamento  ";
        query += " FROM " + tblPersonal;
        query += "  ORDER BY codigo ASC ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            EmpleadosModel cls = new EmpleadosModel();
            cls.setCODIGO(c.getInt(c.getColumnIndex("codigo")));
            cls.setNOMBRE(c.getString(c.getColumnIndex("nombre")));
            cls.setApellido(c.getString(c.getColumnIndex("apellido")));
            cls.setDEPARTAMENTO(c.getInt(c.getColumnIndex("departamento")));
            list.add(cls);
            c.moveToNext();
        }
        this.db.close();
        return list;
    }

    public EmpleadosModel selectPersonal(int Id) {
        EmpleadosModel cls = new EmpleadosModel();
        this.db = getWritableDatabase();
        query = "";
        query += "SELECT ";
        query += " Id ";
        query += " ,codigo    ";
        query += " ,nombre   ";
        query += " ,apellido   ";
        query += " ,departamento  ";
        query += " FROM " + tblPersonal;
        query += " WHERE codigo = " + Id;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            cls.setCODIGO(c.getInt(c.getColumnIndex("codigo")));
            cls.setNOMBRE(c.getString(c.getColumnIndex("nombre")));
            cls.setApellido(c.getString(c.getColumnIndex("apellido")));
            cls.setDEPARTAMENTO(c.getInt(c.getColumnIndex("departamento")));
            c.moveToNext();
        }
        this.db.close();
        return cls;
    }

    public ArrayList selectEntregaArticulo() {
        ArrayList<EntregaArticuloModel> list = new ArrayList<>();
        this.db = getWritableDatabase();

        query = "";
        query += "SELECT ";
        query += " a.Id ";
        query += " ,a.articulodescripcion   ";
        query += " ,a.codigoarticulo   ";
        query += " ,a.cantidad  ";
        query += " ,a.empleado  ";
        query += " ,b.nombre  || ' ' || b.apellido AS NombreEmpleado";
        query += " ,a.fechaentrega  ";
        query += " ,a.responsable   ";
        query += " ,a.Transferido ";
        query += " FROM " + tblEntregaArticulo +" a";
        query += " INNER JOIN  " + tblPersonal +" b ON a.empleado = b.codigo";
        query += "  ORDER BY a.articulodescripcion ASC ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            EntregaArticuloModel cls = new EntregaArticuloModel();
            cls.setCODIGOARTICULO(c.getInt(c.getColumnIndex("codigoarticulo")));
            cls.setARTICULODESCRIPCION(c.getString(c.getColumnIndex("articulodescripcion")));
            cls.setCANTIDAD(c.getInt(c.getColumnIndex("cantidad")));
            cls.setEMPLEADO(c.getInt(c.getColumnIndex("empleado")));
            cls.setNOMBREEMPLEADO(c.getString(c.getColumnIndex("NombreEmpleado")));
            cls.setFECHAENTREGA(c.getString(c.getColumnIndex("fechaentrega")));
            cls.setRESPONSABLE(c.getInt(c.getColumnIndex("responsable")));
            cls.setTRANSFERIDO(c.getInt(c.getColumnIndex("Transferido")));

            list.add(cls);
            c.moveToNext();
        }
        this.db.close();
        return list;
    }

    public EntregaArticuloModel selectEntregaArticulo(int Id) {
        EntregaArticuloModel cls = new EntregaArticuloModel();
        this.db = getWritableDatabase();
        query = "";
        query += "SELECT ";
        query += " Id ";
        query += " ,articulodescripcion   ";
        query += " ,codigoarticulo   ";
        query += " ,cantidad  ";
        query += " ,empleado  ";
        query += " ,fechaentrega  ";
        query += " ,responsable   ";
        query += " ,Transferido ";
        query += " FROM " + tblEntregaArticulo;
        query += " WHERE id = " + Id;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            cls.setCODIGOARTICULO(c.getInt(c.getColumnIndex("codigoarticulo")));
            cls.setARTICULODESCRIPCION(c.getString(c.getColumnIndex("articulodescripcion")));
            cls.setCANTIDAD(c.getInt(c.getColumnIndex("cantidad")));
            cls.setEMPLEADO(c.getInt(c.getColumnIndex("empleado")));
            cls.setFECHAENTREGA(c.getString(c.getColumnIndex("fechaentrega")));
            cls.setRESPONSABLE(c.getInt(c.getColumnIndex("responsable")));
            cls.setTRANSFERIDO(c.getInt(c.getColumnIndex("Transferido")));
            c.moveToNext();
        }
        this.db.close();
        return cls;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="(Exists)">
    public Boolean existsEntregaTransferida(int Id) {
        db = getWritableDatabase();
        query = "";
        boolean exist = true;

        query = " SELECT Id, codigoarticulo ,Transferido ";
        query += " FROM " + tblEntregaArticulo;
        query += " WHERE Id = " + Id;
        query += " AND Transferido  = 1 ";

        Cursor c = db.rawQuery(query, null);
        if (c.getCount() <= 0) {
            exist = false;
        }
        c.close();
        db.close();
        return exist;
    }

    public Boolean existsArticulo(int Id) {
        db = getWritableDatabase();
        query = "";
        boolean exist = true;

        query = " SELECT Id, codigoArticulo  ";
        query += " FROM " + tblArticulo;
        query += " WHERE codigoArticulo = " + Id;

        Cursor c = db.rawQuery(query, null);
        if (c.getCount() <= 0) {
            exist = false;
        }
        c.close();
        db.close();
        return exist;
    }

    public Boolean existsPersonal(int Id) {
        db = getWritableDatabase();
        query = "";
        boolean exist = true;

        query = " SELECT Id, codigo  ";
        query += " FROM " + tblPersonal;
        query += " WHERE codigo = " + Id;

        Cursor c = db.rawQuery(query, null);
        if (c.getCount() <= 0) {
            exist = false;
        }
        c.close();
        db.close();
        return exist;
    }

    // </editor-fold>

}
