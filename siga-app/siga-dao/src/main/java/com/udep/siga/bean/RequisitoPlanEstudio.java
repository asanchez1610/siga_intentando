package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoRequisitoPlan;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class RequisitoPlanEstudio implements Serializable{
    
    private int id;
    private String valor;    
    private TipoRequisitoPlan tipoRequisitoPlan;
    
    /* Utiles */
    private List<RequisitoTipoAsignatura> requisitoTipoAsignaturaList;
    private String valorActual;
    private boolean cumpleRequisito;
    
    public RequisitoPlanEstudio(){
        requisitoTipoAsignaturaList = new ArrayList<RequisitoTipoAsignatura>();
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
     * @return the tipoRequisitoPlan
     */
    public TipoRequisitoPlan getTipoRequisitoPlan() {
        return tipoRequisitoPlan;
    }

    /**
     * @param tipoRequisitoPlan the tipoRequisitoPlan to set
     */
    public void setTipoRequisitoPlan(TipoRequisitoPlan tipoRequisitoPlan) {
        this.tipoRequisitoPlan = tipoRequisitoPlan;
    }

    /**
     * @return the requisitoTipoAsignaturaList
     */
    public List<RequisitoTipoAsignatura> getRequisitoTipoAsignaturaList() {
        return requisitoTipoAsignaturaList;
    }

    /**
     * @param requisitoTipoAsignaturaList the requisitoTipoAsignaturaList to set
     */
    public void setRequisitoTipoAsignaturaList(List<RequisitoTipoAsignatura> requisitoTipoAsignaturaList) {
        this.requisitoTipoAsignaturaList = requisitoTipoAsignaturaList;
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
