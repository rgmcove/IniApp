package co.com.proing.iniapp.utilidades;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Utiles extends AppCompatActivity {

    public void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
