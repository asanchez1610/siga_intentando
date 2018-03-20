package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class EmpresaCV extends TrabajoCV implements Serializable{

    private String cargo;
    private String centroTrabajo;

    public EmpresaCV() {
    }

    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * @return the centroTrabajo
     */
    public String getCentroTrabajo() {
        return centroTrabajo;
    }

    /**
     * @param centroTrabajo the centroTrabajo to set
     */
    public void setCentroTrabajo(String centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }
}
