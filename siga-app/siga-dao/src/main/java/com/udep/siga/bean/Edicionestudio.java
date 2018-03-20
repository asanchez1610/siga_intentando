package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.PeriodicidadPlanEstudio;
import com.udep.siga.bean.enumeration.SistemaHorario;
import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class Edicionestudio implements Serializable{
    private int id;
    private PeriodicidadPlanEstudio periodicidadPlanEstudio;
    private SistemaHorario sistemaHorario;
    private Estudio estudio;
    
    public Edicionestudio(){
        
    }
    
    public Edicionestudio(int id){
        this.id = id;
    }
    
    public Edicionestudio(int id, int idEstudio){
        this.id = id;
        this.estudio = new Estudio(idEstudio);
    }
    
    public Edicionestudio(int id, int idPeriodicidadPlanEstudio, int idEstudio){
        this.id = id;
        this.estudio = new Estudio(idEstudio);
        this.periodicidadPlanEstudio = PeriodicidadPlanEstudio.parse(idPeriodicidadPlanEstudio);
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
     * @return the periodicidadPlanEstudio
     */
    public PeriodicidadPlanEstudio getPeriodicidadPlanEstudio() {
        return periodicidadPlanEstudio;
    }

    /**
     * @param periodicidadPlanEstudio the periodicidadPlanEstudio to set
     */
    public void setPeriodicidadPlanEstudio(PeriodicidadPlanEstudio periodicidadPlanEstudio) {
        this.periodicidadPlanEstudio = periodicidadPlanEstudio;
    }

    /**
     * @return the sistemaHorario
     */
    public SistemaHorario getSistemaHorario() {
        return sistemaHorario;
    }

    /**
     * @param sistemaHorario the sistemaHorario to set
     */
    public void setSistemaHorario(SistemaHorario sistemaHorario) {
        this.sistemaHorario = sistemaHorario;
    }

    /**
     * @return the estudio
     */
    public Estudio getEstudio() {
        return estudio;
    }

    /**
     * @param estudio the estudio to set
     */
    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }
}
