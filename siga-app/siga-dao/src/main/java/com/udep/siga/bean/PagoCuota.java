package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.enumeration.EstadoPagoCuota;
import com.udep.siga.bean.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class PagoCuota implements Serializable{

    private int id;
    private int cuota;
    private Date fecha;
    private float importe;
    private EstadoPagoCuota estadoCuota;

    public PagoCuota() {
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
     * @return the cuota
     */
    public int getCuota() {
        return cuota;
    }

    /**
     * @param cuota the cuota to set
     */
    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    /**
     * @return the fecha
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the importe
     */
    public float getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(float importe) {
        this.importe = importe;
    }

    /**
     * @return the estadoCuota
     */
    public EstadoPagoCuota getEstadoCuota() {
        return estadoCuota;
    }

    /**
     * @param estadoCuota the estadoCuota to set
     */
    public void setEstadoCuota(EstadoPagoCuota estadoCuota) {
        this.estadoCuota = estadoCuota;
    }

}
