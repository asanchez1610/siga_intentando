package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class CronogramaPago implements Serializable{

    private String periodoAcademico;
    private List<PagoCuota> pagoCuotaList;

    public CronogramaPago() {
    }

    /**
     * @return the periodoAcademico
     */
    public String getPeriodoAcademico() {
        return periodoAcademico;
    }

    /**
     * @param periodoAcademico the periodoAcademico to set
     */
    public void setPeriodoAcademico(String periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    /**
     * @return the pagoCuotaList
     */
    public List<PagoCuota> getPagoCuotaList() {
        return pagoCuotaList;
    }

    /**
     * @param pagoCuotaList the pagoCuotaList to set
     */
    public void setPagoCuotaList(List<PagoCuota> pagosCuotasList) {
        this.pagoCuotaList = pagosCuotasList;
    }

    
}
