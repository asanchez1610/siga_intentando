/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.enumeration.TipoPagoEspecial;
import com.udep.siga.bean.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class FechaCuotaPagoEspecial implements Serializable {

    private int cuota;
    private Date fecha;
    private PagoEspecial pagoEspecial;
    private TipoPagoEspecial tipoPagoEspecial;

    public FechaCuotaPagoEspecial() {
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
    @JsonSerialize(using = JsonDateSerializer.class)
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
     * @return the pagoEspecial
     */
    public PagoEspecial getPagoEspecial() {
        return pagoEspecial;
    }

    /**
     * @param pagoEspecial the pagoEspecial to set
     */
    public void setPagoEspecial(PagoEspecial pagoEspecial) {
        this.pagoEspecial = pagoEspecial;
    }

    /**
     * @return the tipoPagoEspecial
     */
    public TipoPagoEspecial getTipoPagoEspecial() {
        return tipoPagoEspecial;
    }

    /**
     * @param tipoPagoEspecial the tipoPagoEspecial to set
     */
    public void setTipoPagoEspecial(TipoPagoEspecial tipoPagoEspecial) {
        this.tipoPagoEspecial = tipoPagoEspecial;
    }
}
