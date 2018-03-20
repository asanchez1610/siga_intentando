package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CLCCategActividadInvestigacion extends CLCCategoriaGeneral implements Serializable{

    private Persona profesor;
    private String nombre;
    private int horasReconocidas;

    public CLCCategActividadInvestigacion() {
    }

    /**
     * @return the profesor
     */
    public Persona getProfesor() {
        return profesor;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setProfesor(Persona profesor) {
        this.profesor = profesor;
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
     * @return the horasReconocidas
     */
    public int getHorasReconocidas() {
        return horasReconocidas;
    }

    /**
     * @param horasReconocidas the horasReconocidas to set
     */
    public void setHorasReconocidas(int horasReconocidas) {
        this.horasReconocidas = horasReconocidas;
    }
}
