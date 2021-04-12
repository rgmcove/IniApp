package co.com.proing.iniapp.presentacion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    ConsultaDB db;

    //Declarar elementos del activity_listar
    private ListView listViewDetalle;
    String idRegistro;
//    Button eliminar, actualizar;

    //Utilidades
    Utiles utiles;

    Context mContext;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        //ACTIVAR EL BOTON ATRAS
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
//            actionBar.setDisplayShowHomeEnabled(true);
        }

        mContext = this;
        utiles = new Utiles();
        db = new ConsultaDB();
        bundle = getIntent().getExtras();

        idRegistro = bundle.getString("id");

        //Inicia lista
        listDetalle = new ArrayList<>();

        //Inicia Views
        listViewDetalle = (ListView) findViewById(R.id.itemListDet);

        //Inicia Botones
        final Button eliminar = findViewById(R.id.idbtnEliminar);
        final Button actualizar = findViewById(R.id.idbtnActualizar);

        //DATOS OBTENIDOS DE LA BD
        ArrayList<String> res = db.Total(idRegistro);
        System.out.println("ARRAY POSICION######################################################: "+res.get(0));
        if (res.size() <= 0) {
            utiles.notificar("No hay Registros", mContext);
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

        Intent intent = new Intent(mContext, ListarActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void eliminar(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle("ELIMINAR REGISTRO");
        alerta.setMessage("¿Desea eliminar el registro?");
        alerta.setIcon(android.R.drawable.ic_dialog_alert);

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("##################GUARDANDO NUEVO REGISTRO#######################################");

                if (db.eliminarDatos(idRegistro)) {
                    utiles.notificar("ELIMINO CON EXITO", mContext);
                    Intent intent = new Intent(mContext, ListarActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    utiles.notificar("ERROR AL ELIMINAR", mContext);
                }
            }
        });

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });

        //ALERTA
        alerta.show();
    }

    public void actualizar(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle("ACTUALIZAR REGISTRO");
        alerta.setMessage("¿Desea actualizar el registro?");
        alerta.setIcon(android.R.drawable.ic_dialog_alert);

        alerta.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("##################IENDO A PANTALLA ACTUALIZAR#######################################");

                Intent intent = new Intent(mContext, ListarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alerta.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });

        //ALERTA
        alerta.show();
    }
}