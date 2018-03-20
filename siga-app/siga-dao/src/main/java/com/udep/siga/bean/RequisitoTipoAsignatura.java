package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoAsignatura;
import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class RequisitoTipoAsignatura implements Serializable{
    
    private TipoAsignatura tipoAsignatura;
    private String valor;

    /* Utiles */
    private String valorActual;
    private boolean cumpleRequisito;
    
    /**
     * @return the tipoAsignatura
     */
    public TipoAsignatura getTipoAsignatura() {
        return tipoAsignatura;
    }

    /**
     * @param tipoAsignatura the tipoAsignatura to set
     */
    public void setTipoAsignatura(TipoAsignatura tipoAsignatura) {
        this.tipoAsignatura = tipoAsignatura;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the valorActual
     */
    public String getValorActual() {
        return valorActual;
    }

    /**
     * @param valorActual the valorActual to set
     */
    public void setValorActual(String valorActual) {
        this.valorActual = valorActual;
    }

    /**
     * @return the cumpleRequisito
     */
    public boolean isCumpleRequisito() {
        return cumpleRequisito;
    }

    /**
     * @param cumpleRequisito the cumpleRequisito to set
     */
    public void setCumpleRequisito(boolean cumpleRequisito) {
        this.cumpleRequisito = cumpleRequisito;
    }
}
