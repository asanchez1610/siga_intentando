package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CLCCategActividadProyectoSocial extends CLCCategoriaGeneral implements Serializable{

    private String organizadora;
    private String beneficiada;
    private int horasDedicadas;

    public CLCCategActividadProyectoSocial() {
    }

    /**
     * @return the organizadora
     */
    public String getOrganizadora() {
        return organizadora;
    }

    /**
     * @param organizadora the organizadora to set
     */
    public void setOrganizadora(String organizadora) {
        this.organizadora = organizadora;
    }

    /**
     * @return the beneficiada
     */
    public String getBeneficiada() {
        return beneficiada;
    }

    /**
     * @param beneficiada the beneficiada to set
     */
    public void setBeneficiada(String beneficiada) {
        this.beneficiada = beneficiada;
    }

    /**
     * @return the horasDedicadas
     */
    public int getHorasDedicadas() {
        return horasDedicadas;
    }

    /**
     * @param horasDedicadas the horasDedicadas to set
     */
    public void setHorasDedicadas(int horasDedicadas) {
        this.horasDedicadas = horasDedicadas;
    }
}
