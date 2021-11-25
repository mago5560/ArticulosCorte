package com.luma.articuloscorte.customs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fuction {

    Global var= Global.getInstance();

    public String getFechaHoraActual() {
        Date date = new Date();
        DateFormat houFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String FechaHora = houFormat.format(date);
        return FechaHora;
    }

    public String getFechaActual() {
        Date date = new Date();
        DateFormat houFormat = new SimpleDateFormat("dd/MM/yyyy");
        String FechaHora = houFormat.format(date);
        return FechaHora;
    }

    public String getHoraActual() {
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String Hora = hourFormat.format(date);
        return Hora;
    }


    public String getVersion(Context context) {
        int currentVersionCode = 0;
        String currentVersionName = "";
        try {

            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            currentVersionCode = packageInfo.versionCode;
            currentVersionName = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AutoUpdate", "Ha habido un error con el packete :S", e);
        }
        return "Version: " + Integer.toString(currentVersionCode) + "." + currentVersionName;
    }



    public String getVersionDB(Context context){
        DBHandler  dbHandler = new DBHandler(context, null, null, 1);
        return dbHandler.getDbVersion();
    }



    public AlertDialog mensaje(String Mensaje, Activity Macty) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Macty);
        AlertDialog alerta;
        builder.setCancelable(false);

        builder.setTitle("Mensaje del Sistema");
        builder.setMessage(Mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alerta = builder.create();
        return alerta;
    }


    public AlertDialog mensajeError(String Mensaje, Activity Macty) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Macty);
        AlertDialog alerta;
        builder.setCancelable(false);

        builder.setTitle("Mensaje de Error");
        builder.setMessage("Se detecto un error, favor de reporta a TI.\nError referencia " + Mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alerta = builder.create();
        return alerta;
    }

    public void msgToast(String Mensaje, Context context) {
        Toast.makeText(context, Mensaje, Toast.LENGTH_LONG).show();
    }

    public void msgSnackBar(String mensaje, Context context) {
        Snackbar.make(((Activity) context).getWindow().getDecorView().getRootView(), mensaje, Snackbar.LENGTH_LONG)
                .show();
    }


    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    public void getHoraDialog(Context context, final TextView control) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String horaF = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoF = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);

                control.setText(horaF + DOS_PUNTOS + minutoF + DOS_PUNTOS + "00");
            }
        }, hour, minute, true);
        timePickerDialog.setTitle("Seleccione la Hora");
        timePickerDialog.show();
    }

    public void getHoraDialog(Context context, final EditText control) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String horaF = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoF = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);

                control.setText(horaF + DOS_PUNTOS + minutoF + DOS_PUNTOS + "00");
            }
        }, hour, minute, true);
        timePickerDialog.setTitle("Seleccione la Hora");
        timePickerDialog.show();
    }

    private static final String DIAGONAL = "/";

    public void getFechaDialog(Context context, final TextView control) {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dataPickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Se coloca +1 debido a que android coloca el mes seleccionado -1
                month += 1;
                String smonth = (month < 10) ? String.valueOf(CERO + month) : String.valueOf(month);
                String sdayOfMonth = (dayOfMonth < 10) ? String.valueOf(CERO + dayOfMonth) : String.valueOf(dayOfMonth);
                control.setText(sdayOfMonth + DIAGONAL + smonth + DIAGONAL + year);
            }
        }, mYear, mMonth, mDay);
        dataPickerDialog.setTitle("Seleccione la Fecha");
        dataPickerDialog.show();
    }

    public void getFechaDialog(Context context, final EditText control) {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dataPickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Se coloca +1 debido a que android coloca el mes seleccionado -1 y tambien el dia para colocarle el 0 a los numeros < 10
                month += 1;
                String smonth = (month < 10) ? String.valueOf(CERO + month) : String.valueOf(month);
                String sdayOfMonth = (dayOfMonth < 10) ? String.valueOf(CERO + dayOfMonth) : String.valueOf(dayOfMonth);
                control.setText(sdayOfMonth + DIAGONAL + smonth + DIAGONAL + year);
            }
        }, mYear, mMonth, mDay);
        dataPickerDialog.setTitle("Seleccione la Fecha");
        dataPickerDialog.show();
    }

    public boolean validarCampoVacio(EditText editText) {
        String Cadena = editText.getText().toString();
        if (TextUtils.isEmpty(Cadena)) {
            editText.setError("Campo requerido");
            editText.requestFocus();
            return true;
        } else {
            editText.setError(null);
        }
        return false;
    }


    public String getPath() {
        String picturePath = "";
        File file = new File(Environment.getExternalStorageDirectory(), "DCIM" + var.getPATH_FILE_APP());
        boolean isDirectoryCreated = file.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = file.mkdirs();
        }
        if (isDirectoryCreated) {
            picturePath = Environment.getExternalStorageDirectory() + File.separator + "DCIM" + var.getPATH_FILE_APP() + File.separator;
        }
        return picturePath;
    }

}
