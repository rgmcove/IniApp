package co.com.proing.iniapp.accesodatos;

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

    public ArrayList<String> Total(){

        db = new ConexionDB();

        String id = "";
        String descripcion = "";
        String estado = "";
        String fecha = "";
        String hora = "";
        String usuario = "";

        ArrayList<String> res = new ArrayList<String>();

        conexion = db.conectarBD();

        try {
            if(conexion!=null) {

                Statement st = conexion.createStatement();
                String sql;

                sql = "select id, descripcion, estado, fecha, hora, usuario_crea from ejercicio_android";

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
                        descripcion = resultSet.getString(2);
//                        res.add(descripcion);
                        estado = resultSet.getString(3);
//                        res.add(estado);
                        fecha = resultSet.getString(4);
//                        res.add(fecha);
                        hora = resultSet.getString(5);
//                        res.add(hora);
                        usuario = resultSet.getString(6);
                        res.add(id+ " - "+descripcion+" - "+estado+" - "+fecha+" - "+hora+" - "+usuario);
                    }
                    while (resultSet.next());
                }

                resultSet.close();
                st.close();
                conexion.close();

                return res;
            }else{
                System.out.println("######################################################## NO OBTUVO RESULTADOS");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
