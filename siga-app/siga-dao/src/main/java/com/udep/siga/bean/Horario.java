package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.Dia;
import com.udep.siga.bean.enumeration.TipoClase;
import java.io.Serializable;




/**
 *
 * @author Wilfredo Atoche
 */
public class Horario implements Serializable{
    private AsignaturaDictada asignaturaDictada;
    private Dia dia;
    private TipoClase tipoClase;
    private BloqueHorario bloqueHorario;
    private Aula aula;
    
    public Horario(){}

    /**
     * @return the asignaturaDictada
     */
    public AsignaturaDictada getAsignaturaDictada() {
        return asignaturaDictada;
    }

    /**
     * @param asignaturaDictada the asignaturaDictada to set
     */
    public void setAsignaturaDictada(AsignaturaDictada asignaturaDictada) {
        this.asignaturaDictada = asignaturaDictada;
    }

    /**
     * @return the dia
     */
    public Dia getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(Dia dia) {
        this.dia = dia;
    }

    /**
     * @return the tipoClase
     */
    public TipoClase getTipoClase() {
        return tipoClase;
    }

    /**
     * @param tipoClase the tipoClase to set
     */
    public void setTipoClase(TipoClase tipoClase) {
        this.tipoClase = tipoClase;
    }

    /**
     * @return the bloqueHorario
     */
    public BloqueHorario getBloqueHorario() {
        return bloqueHorario;
    }

    /**
     * @param bloqueHorario the bloqueHorario to set
     */
    public void setBloqueHorario(BloqueHorario bloqueHorario) {
        this.bloqueHorario = bloqueHorario;
    }

    /**
     * @return the aula
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * @param aula the aula to set
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }
}
