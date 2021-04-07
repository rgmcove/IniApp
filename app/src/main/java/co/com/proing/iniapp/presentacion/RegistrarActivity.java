package co.com.proing.iniapp.presentacion;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import co.com.proing.iniapp.R;

public class RegistrarActivity extends AppCompatActivity {

    //Declarar elementos del activity_registrar
    TextView descripcion;
    EditText txtDescripcion;
    TextView estado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);


    }
}
