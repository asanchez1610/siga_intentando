package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CLCCategCapacitacionProfesional extends CLCCategoriaGeneral implements Serializable{

    private String tipoEvento;
    private String nombreEvento;
    private int horasReconocidas;

    public CLCCategCapacitacionProfesional() {
    }

    /**
     * @return the tipoEvento
     */
    public String getTipoEvento() {
        return tipoEvento;
    }

    /**
     * @param tipoEvento the tipoEvento to set
     */
    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    /**
     * @return the nombreEvento
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * @param nombreEvento the nombreEvento to set
     */
    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
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
