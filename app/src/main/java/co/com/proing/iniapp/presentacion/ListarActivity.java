package co.com.proing.iniapp.presentacion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import co.com.proing.iniapp.R;
import co.com.proing.iniapp.accesodatos.ConsultaDB;
import co.com.proing.iniapp.utilidades.Utiles;

public class ListarActivity extends AppCompatActivity {

    ArrayList listRegister;

    ArrayAdapter adapterRegister;

    ConsultaDB consultaDB;

    //Declarar elementos del activity_listar
    Button btnRegistrar;
    TextView labelTitulo;
    private ListView listViewRegister;

    //Utilidades
    Utiles utilidades;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        mContext = this;
        utilidades = new Utiles();
        consultaDB = new ConsultaDB();

        //Asignar valores a los elementos
        btnRegistrar = findViewById(R.id.btnRegistrar);
        labelTitulo = findViewById(R.id.textView);

        //Inicia lista
        listRegister = new ArrayList<>();

        //Inicia Views
        listViewRegister = (ListView) findViewById(R.id.itemListReg);

        //DATOS OBTENIDOS DE LA BD
        ArrayList<String> res = consultaDB.Total();

        if(res.size() <= 0) {
            utilidades.notificar("No hay Registros", mContext);
        } else{
            System.out.println("Registros: "+ res.size());
            adapterRegister = new ArrayAdapter(this, R.layout.register_list, res);
            listViewRegister.setAdapter(adapterRegister);

            listViewRegister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(parent.getContext(), "Selecciona: "
                    +parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

                }
            });
        }

        /*/MOSTRAR EN CONSOLA
        if(res!=null){
            if(res.size()>0){
                for(int i=0; i<res.size();i++){
                    System.out.println("################## ESTA VAINA TRAE: "+res.get(i));
                }
            }
        }*/

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro(view);
            }
        });
    }

    public void nuevoRegistro(View view){
        Intent intent = new Intent(this, RegistrarActivity.class);
        startActivity(intent);
        finish();
    }

}
