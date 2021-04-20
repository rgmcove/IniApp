package co.com.proing.iniapp.utilidades;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Utiles extends AppCompatActivity {

    public void notificar(String mensaje, Context ct){
        Toast.makeText(ct, mensaje, Toast.LENGTH_LONG).show();
    }

    public void notificarCor(String mensaje, Context ct){
        Toast.makeText(ct, mensaje, Toast.LENGTH_SHORT).show();
    }
}
