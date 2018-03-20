package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class TipoSolicitud implements Serializable{
    private int id;
    private String nombre;
    private boolean requierePago;

    public TipoSolicitud(){
    }
    
    public TipoSolicitud(int id){
        this.id = id;
    }
    public TipoSolicitud(int id,String nombre){
        this.id = id;
        this.nombre=nombre;
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
     * @return the requierePago
     */
    public boolean isRequierePago() {
        return requierePago;
    }

    /**
     * @param requierePago the requierePago to set
     */
    public void setRequierePago(boolean requierePago) {
        this.requierePago = requierePago;
    }
}
