package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class AsociacionProfesionalCV extends TrabajoCV implements Serializable{

    private String nombre;
    private String puesto;
    
    //utiles
    private String desde;
    private String hasta;

    public AsociacionProfesionalCV() {
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * @return the desde
     */
    @Override
    public String getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    @Override
    public void setDesde(String desde) {
        this.desde = desde;
    }

    /**
     * @return the hasta
     */
    @Override
    public String getHasta() {
        return hasta;
    }

    /**
     * @param hasta the hasta to set
     */
    @Override
    public void setHasta(String hasta) {
        this.hasta = hasta;
    }
}
