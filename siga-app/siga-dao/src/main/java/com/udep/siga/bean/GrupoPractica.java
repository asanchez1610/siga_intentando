package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.Campus;
import java.io.Serializable;


/**
 *
 * @author Wilfredo Atoche
 */
public class GrupoPractica implements Serializable{
    private int id;
    private String nombre;
    private String comentario;
    private Campus campus;
    
    public GrupoPractica(){}
    
    public GrupoPractica(int id, String nombre, String comentario, Campus campus){
        this.id = id;
        this.nombre = nombre;
        this.comentario = comentario;
        this.campus = campus;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the campus
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * @param campus the campus to set
     */
    public void setCampus(Campus campus) {
        this.campus = campus;
    }
    
    
}
