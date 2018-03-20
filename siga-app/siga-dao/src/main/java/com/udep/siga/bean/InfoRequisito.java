package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class InfoRequisito implements Serializable{
    private String grupo;
    private String descripcion;
    
    public InfoRequisito(){        
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
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
}
