package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public class TipoEvaluacion implements Serializable{
    private int id;
    private String nombre;
    
    /* Utiles */
    private List<Evaluacion> evaluacionList;
            
    public TipoEvaluacion(){
    }
    
    public TipoEvaluacion(int id){
        this.id = id;
    }
    
    public TipoEvaluacion(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
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
     * @return the evaluacionList
     */
    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    /**
     * @param evaluacionList the evaluacionList to set
     */
    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }
}
