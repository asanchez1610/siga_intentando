package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class AsesorSugerido implements Serializable{

    private int id;
    private Persona asesor_sugerido_1;
    private Persona asesor_sugerido_2;

    public AsesorSugerido() {
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
     * @return the asesor_sugerido_1
     */
    public Persona getAsesor_sugerido_1() {
        return asesor_sugerido_1;
    }

    /**
     * @param asesor_sugerido_1 the asesor_sugerido_1 to set
     */
    public void setAsesor_sugerido_1(Persona asesor_sugerido_1) {
        this.asesor_sugerido_1 = asesor_sugerido_1;
    }

    /**
     * @return the asesor_sugerido_2
     */
    public Persona getAsesor_sugerido_2() {
        return asesor_sugerido_2;
    }

    /**
     * @param asesor_sugerido_2 the asesor_sugerido_2 to set
     */
    public void setAsesor_sugerido_2(Persona asesor_sugerido_2) {
        this.asesor_sugerido_2 = asesor_sugerido_2;
    }
}
