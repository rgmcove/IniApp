package co.com.proing.iniapp.presentacion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.proing.iniapp.R;
import co.com.proing.iniapp.accesodatos.ConsultaDB;
import co.com.proing.iniapp.utilidades.Utiles;

public class ActualizarActivity extends AppCompatActivity {

    ConsultaDB db;
    Utiles utiles;
    Context mContext;
    Bundle bundle;

    //Declarar elementos del activity actualizar
    Button btnActualizar;
    EditText descripcion;
    Spinner comboEstado;
    String idRegistro, usuario;
    int indice;

    //PREFERENCES
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        //ACTIVAR EL BOTON ATRAS Y QUITAR TITULO
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //Asignar valores a los elementos
        descripcion = findViewById(R.id.idtxtDescripcionA);
        comboEstado = findViewById(R.id.idSpnEstadoA);
        btnActualizar = findViewById(R.id.idbtnActualizar);

        db = new ConsultaDB();
        mContext = this;
        utiles = new Utiles();

        //Bundle
        bundle = getIntent().getExtras();
        idRegistro = bundle.getString("id");

        //PREFERENCES
        preferences = mContext.getSharedPreferences("rag",0);
        usuario = preferences.getString("usuario","");

        //DATOS OBTENIDOS DE LA BD
        ArrayList<String> res = db.Total(idRegistro, "actualizar");
        System.out.println("ARRAY POSICION######################################################: "+res.get(0));

        setCampos(res);

        //LLENADO DE SPINNER
        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(this, R.array.combo_estados, android.R.layout.simple_spinner_dropdown_item);
        comboEstado.setAdapter(adapterEstado);
        comboEstado.setSelection(indiceSpinner(res));

        if (!comboEstado.getSelectedItem().toString().contains("SELECCIONE")) {
            comboEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(parent.getContext(), "Seleccionado: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void actualizar(View view) {

        System.out.println("##################ACTUALIZAND REGISTRO#######################################");

        if (!descripcion.getText().toString().equals("") && !comboEstado.getSelectedItem().toString().equals("SELECCIONE")){
            ContentValues conValues = new ContentValues();

            conValues.put("descripcion", descripcion.getText().toString());
            conValues.put("estado", comboEstado.getSelectedItem().toString());
            conValues.put("usuario", usuario);
            conValues.put("id", idRegistro);

            if (db.actualizarDatos(conValues)) {
                utiles.notificar("ACTUALIZO CON EXITO", mContext);
                Intent intent = new Intent(mContext, DetalleActivity.class);
                intent.putExtra("id", idRegistro);
                startActivity(intent);
                finish();
            } else {
                utiles.notificar("ERROR AL ACTUALIZAR", mContext);
            }

        } else {
            utiles.notificar("POR FAVOR INGRESE TODOS LOS DATOS", mContext);
        }
    }

    public void setCampos(ArrayList<String> res){
         descripcion.setText(res.get(1));
    }

    public int indiceSpinner(ArrayList<String> res){

        switch (res.get(2)){

            case "ACTIVO": indice = 1;
                break;
            case "INACTIVO": indice = 2;
                break;
            case "RETIRADO": indice = 3;
                break;
            case "N/A": indice = 4;
                break;
            default: indice = 0;
                break;
        }
        System.out.println("#######################################################################  INDICE DEL SPINNER: "+indice);

        return indice;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(mContext, DetalleActivity.class);
        intent.putExtra("id", idRegistro);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}