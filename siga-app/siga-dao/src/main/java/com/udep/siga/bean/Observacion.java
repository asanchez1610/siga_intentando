package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Observacion implements Serializable{

    private String descripcion;
    private String valor;

    public Observacion() {
    }

    public Observacion(String valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        if (valor == null) {
            return "";
        } else {
            return valor;
        }
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
