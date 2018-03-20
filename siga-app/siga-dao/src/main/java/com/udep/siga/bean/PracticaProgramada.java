package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.Dia;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Wilfredo Atoche
 */
public class PracticaProgramada implements Serializable{
    private int id;
    private Dia dia;
    private GrupoPractica grupoPractica;
    private AsignaturaDictada asignaturaDictada;
    private BloqueHorario bloqueHorario;
    
    
    public PracticaProgramada(){}

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
     * @return the grupoPractica
     */
    public GrupoPractica getGrupoPractica() {
        return grupoPractica;
    }

    /**
     * @param grupoPractica the grupoPractica to set
     */
    public void setGrupoPractica(GrupoPractica grupoPractica) {
        this.grupoPractica = grupoPractica;
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
    
    @Override
    public String toString() {
         return org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
