package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class ConsultoriaCv extends InvestigacionAcademica implements Serializable{

    private String consultoriaEn;
    private String cliente;
    private String participante;

    public ConsultoriaCv() {
    }

    /**
     * @return the consultoriaEn
     */
    public String getConsultoriaEn() {
        return consultoriaEn;
    }

    /**
     * @param consultoriaEn the consultoriaEn to set
     */
    public void setConsultoriaEn(String consultoriaEn) {
        this.consultoriaEn = consultoriaEn;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the participante
     */
    public String getParticipante() {
        return participante;
    }

    /**
     * @param participante the participante to set
     */
    public void setParticipante(String participante) {
        this.participante = participante;
    }
}
