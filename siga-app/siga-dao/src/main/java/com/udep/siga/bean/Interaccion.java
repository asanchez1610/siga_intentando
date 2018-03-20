package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Wilfredo Atoche
 */
public class Interaccion implements Serializable{
    private Date fecha;
    private int duracion;
    private String temaInteraccion;
    
    public Interaccion(){
        
    }

    /**
     * @return the fecha
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the duracion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the temaInteraccion
     */
    public String getTemaInteraccion() {
        return temaInteraccion;
    }

    /**
     * @param temaInteraccion the temaInteraccion to set
     */
    public void setTemaInteraccion(String temaInteraccion) {
        this.temaInteraccion = temaInteraccion;
    }
}
