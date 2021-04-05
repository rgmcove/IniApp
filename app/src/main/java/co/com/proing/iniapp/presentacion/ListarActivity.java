package co.com.proing.iniapp.presentacion;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import co.com.proing.iniapp.R;

public class ListarActivity extends AppCompatActivity {

    private ListView listViewRegister;

    ArrayList listRegister;

    ArrayAdapter adapterRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        //Inicia lista
        listRegister = new ArrayList<>();

        //Inicia Views
        listViewRegister = (ListView) findViewById(R.id.itemListReg);

        if(listRegister.size() <= 0) {
            notificarMensajes("No hay registros");
        } else{
            System.out.println("Registros: "+ listRegister.size());
        }
    }

    public void notificarMensajes(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

    }
}
