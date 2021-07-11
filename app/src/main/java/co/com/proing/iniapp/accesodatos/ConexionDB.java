package co.com.proing.iniapp.accesodatos;

import android.os.StrictMode;

import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    Connection conexion = null;

    //Funcion para Conectar a la BD
    public Connection conectarBD(){
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://192.168.0.26:5432/******", "******", "******");
            System.out.println("#############resultado!!!: " + conexion);
        }catch (Exception er){
            System.err.println("################## Error Conexion: "+ er.toString());
        }
        return conexion;
    }

    //Funcion para Cerrar la Conexion
    protected void cerrar_conexion(Connection con)throws Exception{
        con.close();
    }
}
