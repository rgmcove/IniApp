package co.com.proing.iniapp.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.com.proing.iniapp.R;

public class LoginActivity extends AppCompatActivity {

    //Declarar Elementos de activity_login
    EditText txtUsuario;
    EditText txtContraseña;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Asignar valores a cada elemento
        btnIngresar = findViewById(R.id.btnIngresar);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtPassword);

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
            notificarMensajes("El campo usuario o contraseña no pueden estar vacios, por favor ingreselos");

        } else{

            if (user.equals(cedula) && pass.equals(clave)){
                notificarMensajes("Ingreso correctamente");
                Intent intent = new Intent(this, ListarActivity.class);
                startActivity(intent);

            } else{
                notificarMensajes("Usuario o Contraseña incorrectos, ingreslos nuevamente");
            }
        }
    }

    public void notificarMensajes(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

}
