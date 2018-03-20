package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class Aula implements Serializable{
    private int id;
    private String nombre;
    private int capacidadReal;
    private int capacidadAForo;
    
    public Aula(){}
    
    public Aula(int id, String nombre, int capacidadReal, int capacidadAForo){
        this.id = id;
        this.nombre = nombre;
        this.capacidadReal = capacidadReal;
        this.capacidadAForo = capacidadAForo;
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
     * @return the capacidadReal
     */
    public int getCapacidadReal() {
        return capacidadReal;
    }

    /**
     * @param capacidadReal the capacidadReal to set
     */
    public void setCapacidadReal(int capacidadReal) {
        this.capacidadReal = capacidadReal;
    }

    /**
     * @return the capacidadAForo
     */
    public int getCapacidadAForo() {
        return capacidadAForo;
    }

    /**
     * @param capacidadAForo the capacidadAForo to set
     */
    public void setCapacidadAForo(int capacidadAForo) {
        this.capacidadAForo = capacidadAForo;
    }
    
    
            
}
