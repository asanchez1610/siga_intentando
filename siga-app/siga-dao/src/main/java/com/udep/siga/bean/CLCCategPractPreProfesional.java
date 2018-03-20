package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CLCCategPractPreProfesional extends CLCCategoriaGeneral implements Serializable{

    private String empresa;
    private Date desde;
    private Date hasta;
    private int horasReconocidas;

    public CLCCategPractPreProfesional() {
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the desde
     */
    public Date getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    public void setDesde(Date desde) {
        this.desde = desde;
    }

    /**
     * @return the hasta
     */
    public Date getHasta() {
        return hasta;
    }

    /**
     * @param hasta the hasta to set
     */
    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    /**
     * @return the horasReconocidas
     */
    public int getHorasReconocidas() {
        return horasReconocidas;
    }

    /**
     * @param horasReconocidas the horasReconocidas to set
     */
    public void setHorasReconocidas(int horasReconocidas) {
        this.horasReconocidas = horasReconocidas;
    }
}
