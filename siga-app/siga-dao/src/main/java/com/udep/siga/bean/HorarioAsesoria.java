package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.Dia;
import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class HorarioAsesoria implements Serializable{
    private String titulo;
    private Dia dia;
    private BloqueHorario bloqueHorario;
    private String oficina;
    private String edificio;
    
    public HorarioAsesoria(){
        
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
     * @return the oficina
     */
    public String getOficina() {
        return oficina;
    }

    /**
     * @param oficina the oficina to set
     */
    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    /**
     * @return the edificio
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * @param edificio the edificio to set
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }
}
