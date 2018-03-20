package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CLCCategEstudioDirigido extends CLCCategoriaGeneral implements Serializable{

    private Persona profesor;
    private String asignatura;
    private int horasReconocidas;

    public CLCCategEstudioDirigido() {
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
     * @return the asignatura
     */
    public String getAsignatura() {
        return asignatura;
    }

    /**
     * @param asignatura the asignatura to set
     */
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
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
