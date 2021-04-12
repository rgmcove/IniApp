package co.com.proing.iniapp.accesodatos;

import android.content.ContentValues;
import android.content.Context;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConsultaDB {

    private Connection conexion = null;
    Context mContext;

    ConexionDB db;

    public ArrayList<String> Total(String idItem) {

        db = new ConexionDB();

        String id = "";
        String descripcion = "";
        String estado = "";
        String fecha = "";
        String hora = "";
        String usuario_crea = "";
        String usuario_modifica = "";

        ArrayList<String> res = new ArrayList<String>();

        conexion = db.conectarBD();

        try {
            if (conexion != null) {

                Statement st = conexion.createStatement();
                String sql;

                sql = "select \n" +
                        "x.id, \n" +
                        "upper(x.descripcion), \n" +
                        "x.estado, \n" +
                        "x.fecha, \n" +
                        "to_char(x.hora, 'HH24:MI'), \n" +
                        "y.rhdocumento||' '||y.rhnombre||' '||y.rhapell1||' '||y.rhapell2, \n" +
                        "case when x.usuario_modifica is null then 'N/A' else  z.rhdocumento||' '||z.rhnombre||' '||z.rhapell1||' '||z.rhapell2 end \n" +
                        "from ejercicio_android x\n" +
                        "left join rh_hojadevida y on y.rhdocumento = x.usuario_crea\n" +
                        "left join rh_hojadevida z on z.rhdocumento = x.usuario_modifica\n" +
                        "where x.id ='" + idItem + "'";

                ResultSet resultSet = st.executeQuery(sql);

                if (!resultSet.next()) {
                    resultSet.close();
                    st.close();
                    conexion.close();
                    return res;
                } else {

                    do {

                        id = resultSet.getString(1);
                        res.add("ID: " + id);
                        descripcion = resultSet.getString(2);
                        res.add("DESCRIPCIÓN: " + descripcion);
                        estado = resultSet.getString(3);
                        res.add("ESTADO: " + estado);
                        fecha = resultSet.getString(4);
                        res.add("FECHA: " + fecha);
                        hora = resultSet.getString(5);
                        res.add("HORA: " + hora);
                        usuario_crea = resultSet.getString(6);
                        res.add("USUARIO CREO: " + usuario_crea);
                        usuario_modifica = resultSet.getString(7);
                        res.add("USUARIO MODIFICA: " + usuario_modifica);
//                        res.add(id + " - " + descripcion + " - " + estado + " - " + fecha + " - " + hora + " - " + usuario);
                        System.out.println("######################################################## RESULTADOS: " + res);
                    }
                    while (resultSet.next());
                }
                resultSet.close();
                st.close();
                conexion.close();

                return res;
            } else {
                System.out.println("######################################################## NO OBTUVO RESULTADOS");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public boolean registrarDatos(ContentValues conValues) {
        System.out.println("####################### INGRESO A REGISTRO DE DATOS ########################");

        boolean guardado = false;
        int res = 0;
        db = new ConexionDB();
        conexion = db.conectarBD();

        try {
            Statement st = conexion.createStatement();

            String sql;
            sql = "INSERT INTO ejercicio_android (descripcion, estado, fecha, hora, usuario_crea) " +
                    "VALUES(" + "'" + conValues.getAsString("descripcion") + "', '" + conValues.getAsString("estado") + "', '" + conValues.getAsString("fecha") + "'," +
                    " '" + conValues.getAsString("hora") + "', '" + conValues.getAsString("usuario") + "')";

            res = st.executeUpdate(sql);

            st.close();
            conexion.close();

            guardado = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guardado;
    }

    public ArrayList<String> ConsultaParcial() {

        db = new ConexionDB();

        String id = "";
        String estado = "";
        String usuario = "";

        ArrayList<String> res = new ArrayList<String>();

        conexion = db.conectarBD();

        try {
            if (conexion != null) {

                Statement st = conexion.createStatement();
                String sql;

                sql = "select x.id, \n" +
                        "x.estado, \n" +
                        "y.rhdocumento||' '||y.rhnombre||' '||y.rhapell1||' '||y.rhapell2\n" +
                        "from ejercicio_android x\n" +
                        "left join rh_hojadevida y on y.rhdocumento = x.usuario_crea";

                ResultSet resultSet = st.executeQuery(sql);

                if (!resultSet.next()) {
                    resultSet.close();
                    st.close();
                    conexion.close();
                    return res;
                } else {

                    do {

                        id = resultSet.getString(1);
//                        res.add(id);
                        estado = resultSet.getString(2);
//                        res.add(estado);
                        usuario = resultSet.getString(3);
                        res.add(id + " - " + estado + " - " + usuario);
                    }
                    while (resultSet.next());
                }

                resultSet.close();
                st.close();
                conexion.close();

                return res;
            } else {
                System.out.println("######################################################## NO OBTUVO RESULTADOS");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public boolean eliminarDatos(String idRegistro) {

        System.out.println("####################### INGRESO A REGISTRO DE DATOS ########################");

        boolean guardado = false;
        int res = 0;
        db = new ConexionDB();
        conexion = db.conectarBD();

        try {
            Statement st = conexion.createStatement();

            String sql;
            sql = "delete from ejercicio_android \n" +
                    "where id ='"+idRegistro+"'";

            res = st.executeUpdate(sql);

            st.close();
            conexion.close();

            guardado = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guardado;
    }

}