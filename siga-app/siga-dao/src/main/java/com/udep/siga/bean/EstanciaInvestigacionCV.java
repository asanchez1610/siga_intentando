package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class EstanciaInvestigacionCV extends InvestigacionAcademica implements Serializable{

    private String tema;
    private String centroVisitado;
    

    public EstanciaInvestigacionCV() {
    }

    /**
     * @return the tema
     */
    public String getTema() {
        return tema;
    }

    /**
     * @param tema the tema to set
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * @return the centroVisitado
     */
    public String getCentroVisitado() {
        return centroVisitado;
    }

    /**
     * @param centroVisitado the centroVisitado to set
     */
    public void setCentroVisitado(String centroVisitado) {
        this.centroVisitado = centroVisitado;
    }

    
}
