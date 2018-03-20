package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CargoDirectivoCV extends TrabajoCV implements Serializable{

    private String cargo;
    private String institucion;

    public CargoDirectivoCV() {
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
     * @return the institucion
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
}
