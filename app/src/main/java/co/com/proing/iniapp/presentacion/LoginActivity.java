package co.com.proing.iniapp.presentacion;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import co.com.proing.iniapp.R;
import co.com.proing.iniapp.accesodatos.ConexionDB;
import co.com.proing.iniapp.utilidades.Utiles;

public class LoginActivity extends AppCompatActivity {

    //PERMISSION STRINGS
    String[] permissionsArray = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    //Control Permisos
    boolean bolPermisos = false;
    boolean bolCheckPermissions = false;

    //Context
    Context mContext;

    //Utilidades
    Utiles utilidades;

    //Declarar Elementos de activity_login
    EditText txtUsuario;
    EditText txtContraseña;
    Button btnIngresar;

    //BD
    ConexionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        utilidades = new Utiles();
        db = new ConexionDB();

        //Asignar valores a cada elemento
        btnIngresar = findViewById(R.id.btnIngresar);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtPassword);

        if (Build.VERSION.SDK_INT >= 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //VALIDACION DE VERSION ANDROID
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        } else {
            bolPermisos = true;
        }

        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ingresar(view);
            }
        });
    }

    public void ingresar(View view){
        String cedula = "1113654597";
        String clave = "14789";

        String user = txtUsuario.getText().toString();
        String pass = txtContraseña.getText().toString();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            utilidades.notificar("El campo usuario o contraseña no pueden estar vacios, por favor ingreselos", mContext);

        } else{

            if (user.equals(cedula) && pass.equals(clave)){
                utilidades.notificar("Ingreso correctamente", mContext);
                Intent intent = new Intent(this, ListarActivity.class);
                startActivity(intent);
                finish();

            } else{
                utilidades.notificar("Usuario o Contraseña incorrectos, ingreselos nuevamente", mContext);
            }
        }
        db.conectarBD();
    }

    private void checkPermissions(){
        int inResultadoPermiso;
        final int inResultadoForzado = -1;
        String p = "";
        int inContador = 0;

        List<String> listaPermisos = new ArrayList<>();


        for (int i = 0; i < permissionsArray.length; i++) {

            p = permissionsArray[i];

            final String p1 = p;

            inResultadoPermiso = ContextCompat.checkSelfPermission(mContext, p);

            if (inResultadoPermiso != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, p)) {

//                    System.out.println("<<<<<<<<<<<<<<<<< shouldShowRequestPermissionRationale PARA P: "+p);

                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);

                    alerta.setTitle("PERMISO INDISPENSABLE DESHABILITADO");
                    alerta.setMessage("Se requieren todos los permisos para el uso de la aplicación, ¿desea ir a la configuración y habilitar el permiso?");
                    alerta.setIcon(android.R.drawable.ic_dialog_alert);

                    alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //ActivityCompat.requestPermissions(LoginActivity.this,new String[]{p1},inResultadoForzado);
                            bolCheckPermissions = true;
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                        }
                    });

                    alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            System.exit(0);
                        }
                    });

                    //MOSTRAR ALERTA
                    alerta.show();

                } else {
//                    System.out.println("<<<<<<<<<<<<<<<<< PERMISO NO DEBE MOSTRAR ANUNCIO, A LISTA DE DENEGADOS "+p);

                    //ActivityCompat.requestPermissions(LoginActivity.this,new String[]{p},inResultadoPermiso);
                    listaPermisos.add(p);

                }

            } else {
                inContador++;
//                System.out.println("<<<<<<<<<<<<<<<<< INCREMENTO CONTADOR EN VALIDACIÓN INICIAL ");
            }
        }

        if (!listaPermisos.isEmpty()) {
            System.out.println(" LISTAPERMISOS NO ESTÁ VACÍO!!!!!!!!!!!!!!!!!! ");
            ActivityCompat.requestPermissions(this, listaPermisos.toArray(new String[listaPermisos.size()]), 2456);
        } else {
            System.out.println(" LISTAPERMISOS ESTÁ VACÍO!!!!!!!!!!!!!!!!!! ");
            if (inContador == permissionsArray.length) {
//                System.out.println("<<<<<<<<<<<<<<<<< PERMISOS PREVIAMENTE ASEGURADOS, INICIANDO THREAD ");
                //this = new Sincronizacion(main);
//				//gpsManager = new GPSManager(this);
//				//checkGPS();
                bolPermisos = true;
            }
        }
    }

}
