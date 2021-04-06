package co.com.proing.iniapp.presentacion;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import co.com.proing.iniapp.R;
import co.com.proing.iniapp.utilidades.Utiles;

public class ListarActivity extends AppCompatActivity {

    private ListView listViewRegister;

    ArrayList listRegister;

    ArrayAdapter adapterRegister;

    //Utilidades
    Utiles utilidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        //Inicia lista
        listRegister = new ArrayList<>();

        //Inicia Views
        listViewRegister = (ListView) findViewById(R.id.itemListReg);

        if(listRegister.size() <= 0) {
            utilidades.notificar("No hay Registros");
        } else{
            System.out.println("Registros: "+ listRegister.size());
            adapterRegister = new ArrayAdapter(this, R.layout.register_list, listRegister);
            listViewRegister.setAdapter(adapterRegister);
        }
    }

}
