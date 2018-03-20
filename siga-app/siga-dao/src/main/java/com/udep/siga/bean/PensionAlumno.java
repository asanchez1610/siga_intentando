package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class PensionAlumno implements Serializable{

    private int calu;
    private boolean pagoQuincena;
    private boolean especial;

    public PensionAlumno() {
    }

    /**
     * @return the calu
     */
    public int getCalu() {
        return calu;
    }

    /**
     * @param calu the calu to set
     */
    public void setCalu(int calu) {
        this.calu = calu;
    }

    /**
     * @return the pagoQuincena
     */
    public boolean isPagoQuincena() {
        return pagoQuincena;
    }

    /**
     * @param pagoQuincena the pagoQuincena to set
     */
    public void setPagoQuincena(boolean pagoQuincena) {
        this.pagoQuincena = pagoQuincena;
    }

    /**
     * @return the especial
     */
    public boolean isEspecial() {
        return especial;
    }

    /**
     * @param especial the especial to set
     */
    public void setEspecial(boolean especial) {
        this.especial = especial;
    }
}
