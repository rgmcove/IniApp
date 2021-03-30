package co.com.proing.iniapp.modelo;

import java.sql.Time;
import java.util.Date;

public class EjercicioAndroid {

    private String descripcion;
    private Integer estado;
    private Date fecha;
    private Time hora;

    public EjercicioAndroid(String descripcion, Integer estado, Date fecha, Time hora) {
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }
}
