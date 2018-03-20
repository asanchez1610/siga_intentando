package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class EvaluacionAlumno implements Serializable{
    private int id;
    private Nota nota;
    private String notaInformativa;
    private boolean anulada;
    
    public EvaluacionAlumno(){
    }
    
    public EvaluacionAlumno(int id){
        this.id = id;
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
     * @return the nota
     */
    public Nota getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(Nota nota) {
        this.nota = nota;
    }

    /**
     * @return the notaInformativa
     */
    public String getNotaInformativa() {
        return notaInformativa;
    }

    /**
     * @param notaInformativa the notaInformativa to set
     */
    public void setNotaInformativa(String notaInformativa) {
        this.notaInformativa = notaInformativa;
    }

    /**
     * @return the anulada
     */
    public boolean isAnulada() {
        return anulada;
    }

    /**
     * @param anulada the anulada to set
     */
    public void setAnulada(boolean anulada) {
        this.anulada = anulada;
    }
}
