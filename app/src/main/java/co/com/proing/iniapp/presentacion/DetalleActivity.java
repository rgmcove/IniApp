package co.com.proing.iniapp.presentacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.proing.iniapp.R;
import co.com.proing.iniapp.accesodatos.ConsultaDB;
import co.com.proing.iniapp.utilidades.Utiles;

public class DetalleActivity extends AppCompatActivity {

    ArrayList listDetalle;

    ArrayAdapter adapterDetalle;

    ConsultaDB consultaDB;

    //Declarar elementos del activity_listar
    TextView labelTitulo;
    private ListView listViewDetalle;

    //Utilidades
    Utiles utilidades;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        mContext = this;
        utilidades = new Utiles();
        consultaDB = new ConsultaDB();

        //Inicia lista
        listDetalle = new ArrayList<>();

        //Inicia Views
        listViewDetalle = (ListView) findViewById(R.id.itemListDet);

        //DATOS OBTENIDOS DE LA BD
        ArrayList<String> res = consultaDB.Total();

        if (res.size() <= 0) {
            utilidades.notificar("No hay Registros", mContext);
        } else {
            System.out.println("Registros: " + res.size());
            adapterDetalle = new ArrayAdapter(this, R.layout.register_list, res);
            listViewDetalle.setAdapter(adapterDetalle);

            listViewDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(parent.getContext(), "Selecciona: "
                            + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                }
            });

            listViewDetalle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    final int idItem = (int) id;
                    AlertDialog.Builder alerta = new AlertDialog.Builder(mContext);

                    alerta.setTitle("ACCIONES SOBRE EL REGISTRO");
                    alerta.setMessage("¿Desea modificar o eliminar el registro?");
                    alerta.setIcon(android.R.drawable.ic_dialog_alert);

                    alerta.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alerta.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    return false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(mContext);

        alerta.setTitle("REGRESAR A LISTADO ANTERIOR");
        alerta.setMessage("¿Desea regresar al listado anterior?");
        alerta.setIcon(android.R.drawable.ic_dialog_alert);

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(mContext, ListarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        //ALERTA
        alerta.show();
    }
}