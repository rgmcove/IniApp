package co.com.proing.iniapp.presentacion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
    private ListView listViewRegister;

    //Utilidades
    Utiles utilidades;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Quitar barra de titulo
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_listar);

        //ACTIVAR EL BOTON ATRAS Y QUITAR TITULO
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Listado Registros");
//            actionBar.setDisplayShowHomeEnabled(true);
        }

        mContext = this;
        utilidades = new Utiles();
        consultaDB = new ConsultaDB();

        //Asignar valores a los elementos
        btnRegistrar = findViewById(R.id.btnRegistrar);

        //Inicia lista
        listRegister = new ArrayList<>();

        //Inicia Views
        listViewRegister = (ListView) findViewById(R.id.itemListReg);

        //DATOS OBTENIDOS DE LA BD
        ArrayList<String> res = consultaDB.ConsultaParcial();

        if(res.size() <= 0) {
            utilidades.notificar("No hay Registros", mContext);
        } else{
            System.out.println("Registros: "+ res.size());
            adapterRegister = new ArrayAdapter(this, R.layout.register_list, res);
            listViewRegister.setAdapter(adapterRegister);

           /* listViewRegister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(parent.getContext(), "Selecciona: "
                    +parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
                }
            });*/

            listViewRegister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                    final int idItem = (int) position;
                    final String idRegistro;
                    System.out.println("##########################################################################################################################DATO ESCOGIDO: "+idItem);
                    String item = parent.getItemAtPosition(position).toString();
                    System.out.println("##########################################################################################################################ITEM ESCOGIDO: "+item);
                    final char idcadena = item.charAt(1);
                    System.out.println("##########################################################################################################################ITEM CADENA: "+idcadena);
                    if (Character.isWhitespace(idcadena)){
                        String idtable = item.substring(0,1);
                        idRegistro = idtable;
                        System.out.println("##########################################################################################################################ITEM FINAL: "+idtable);
                    } else {
                        String idtable = item.substring(0,2);
                        idRegistro = idtable;
                        System.out.println("##########################################################################################################################ITEM FINAL: "+idtable);
                    }

                    AlertDialog.Builder alerta = new AlertDialog.Builder(mContext);

                    alerta.setTitle("DETALLE REGISTRO");
                    alerta.setMessage("??Desea ver el detalle del registro?");
                    alerta.setIcon(android.R.drawable.ic_dialog_alert);

                    alerta.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            irDetalle(view, idRegistro);
                        }
                    });

                    alerta.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    //ALERTA
                    alerta.show();

//                    return false;
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

    public void irDetalle(View view, String idTable){
        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra("id", idTable);
        System.out.println("##################?????????????????????????????????????????????? ESTA VAINA TRAE: "+idTable);
        startActivity(intent);
        finish();
    }

}
