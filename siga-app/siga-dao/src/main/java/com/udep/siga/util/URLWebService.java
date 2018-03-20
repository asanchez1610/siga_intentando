package com.udep.siga.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gary Ayala
 */
public enum URLWebService {
    infoAlumnoEstudio("argus/infoAlumnoEstudio.json"),
    alumnoTipoBeca("argus/alumnoTipoBeca.json"),
    historialAcademicoIdiomas("argus/historialAcademicoCI.json"),
    alumnoIsMoroso("argus/verifyAlumnoMoroso.json"),
    alumnoDebeMatricula("argus/verifyDeudaMatricula.json"),
    alumnoCronogramaPagos("argus/alumnoCronogramaPagos.json"),
    alumnoCuotasdePagos("argus/alumnoCuotasdePagos.json"),
    alumnoRecibodePagos("argus/alumnoRecibodePagos.json"),
    alumnoResponsableEconomico("argus/alumnoDatosFamiliares.json"),
    alumnoInfoMiembro("argus/alumnoInfoMiembro.json"),
    alumnoInfoMiembroUpdate("argus/alumnoInfoMiembroUpdate.json");
    
    private final String url;
    
    URLWebService(String url){
        this.url = url;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
}
