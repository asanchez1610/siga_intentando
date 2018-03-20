package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CLCCategActividadDisney extends CLCCategoriaGeneral implements Serializable{

    private String asignatura;
    private int horasReconocidas;

    public CLCCategActividadDisney() {
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
