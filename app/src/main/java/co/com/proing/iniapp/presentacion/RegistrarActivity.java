package co.com.proing.iniapp.presentacion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import co.com.proing.iniapp.R;
import co.com.proing.iniapp.accesodatos.ConsultaDB;
import co.com.proing.iniapp.utilidades.Utiles;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener {

    //Declarar elementos del activity_registrar
    EditText efecha, ehora;
    TextInputEditText descripcion;
    Spinner comboEstado;
    String usuario;
    Button bfecha, bhora, btnRegistrar;
    private int dia, mes, anio, hora, minutos;

    ConsultaDB db;
    Utiles utiles;
    Context mContext;

    //PREFERENCES
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //ACTIVAR EL BOTON ATRAS Y QUITAR TITULO
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Nuevo Registro");
//            actionBar.setDisplayShowHomeEnabled(true);
        }

        //Asignar valores a los elementos
        descripcion = findViewById(R.id.idtxtDescripcion);
        comboEstado = findViewById(R.id.idSpnEstado);
        bfecha = findViewById(R.id.idbtnFecha);
        bhora = findViewById(R.id.idbtHora);
        efecha = findViewById(R.id.idtxtFecha);
        ehora = findViewById(R.id.idtxtHora);
//        usuario = findViewById(R.id.idtxtUsuario);
        btnRegistrar = findViewById(R.id.idbtnRegistrar);

        db = new ConsultaDB();
        mContext = this;
        utiles = new Utiles();

        //PREFERENCES
        preferences = mContext.getSharedPreferences("rag",0);
        usuario = preferences.getString("usuario","");

        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(this, R.array.combo_estados, android.R.layout.simple_spinner_dropdown_item);
        comboEstado.setAdapter(adapterEstado);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == bfecha) {
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    efecha.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
            }, anio, mes, dia);
            datePickerDialog.show();
        }
        if (v == bhora) {
            final Calendar calendar = Calendar.getInstance();
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minutos = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    ehora.setText(hourOfDay + ":" + minute);
                }
            }, hora, minutos, true);
            timePickerDialog.show();
        }
    }

    public void registrarNuevo(View view) {

        System.out.println("##################GUARDANDO NUEVO REGISTRO#######################################");

        if (!descripcion.getText().toString().equals("") && !efecha.getText().toString().equals("") && !ehora.getText().toString().equals("")){
            ContentValues conValues = new ContentValues();

            conValues.put("descripcion", descripcion.getText().toString());
            conValues.put("estado", comboEstado.getSelectedItem().toString());
            conValues.put("fecha", efecha.getText().toString());
            conValues.put("hora", ehora.getText().toString());
            conValues.put("usuario", usuario);

            if (db.registrarDatos(conValues)) {
                utiles.notificar("GUARDO CON EXITO", mContext);
                Intent intent = new Intent(mContext, ListarActivity.class);
                startActivity(intent);
                finish();
            } else {
                utiles.notificar("ERROR AL GUARDAR", mContext);
            }

        } else {
            utiles.notificar("NO PUEDE GUARDAR UN REGISTRO SIN DATOS", mContext);
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
}
