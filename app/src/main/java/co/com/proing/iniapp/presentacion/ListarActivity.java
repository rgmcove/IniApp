package co.com.proing.iniapp.presentacion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.com.proing.iniapp.R;
import co.com.proing.iniapp.accesodatos.ConexionDB;

public class ListarActivity extends AppCompatActivity {

    ConexionDB db = new ConexionDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
    }
}
