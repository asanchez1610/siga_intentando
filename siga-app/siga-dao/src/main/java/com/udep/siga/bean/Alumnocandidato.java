package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Andy
 */
public class Alumnocandidato implements Serializable{
        private Integer idalumnocandidato;
        private Integer idalumno;
        private String alumnostr;
	private  Alumno alumno;
        private Integer idedicionetudio;
	private Integer idperiodoacademico;
	private Integer idcampus;
	private Integer nivel;
        private Integer cantidadvotos;
    public Alumnocandidato()
    {
        
    }
    /**
     * @return the idalumnocandidato
     */
    public Integer getIdalumnocandidato() {
        return idalumnocandidato;
    }

    /**
     * @param idalumnocandidato the idalumnocandidato to set
     */
    public void setIdalumnocandidato(Integer idalumnocandidato) {
        this.idalumnocandidato = idalumnocandidato;
    }

    /**
     * @return the idalumno
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * @param idalumno the idalumno to set
     */
    public void setIdalumno(Integer idalumno) {
        this.idalumno = idalumno;
    }
    public Integer getIdalumno() {
        return idalumno;
    }
   public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    /**
     * @return the idperiodoacademico
     */
    public Integer getIdperiodoacademico() {
        return idperiodoacademico;
    }

    /**
     * @param idperiodoacademico the idperiodoacademico to set
     */
    public void setIdperiodoacademico(Integer idperiodoacademico) {
        this.idperiodoacademico = idperiodoacademico;
    }

    /**
     * @return the idcampus
     */
    public Integer getIdcampus() {
        return idcampus;
    }

    /**
     * @param idcampus the idcampus to set
     */
    public void setIdcampus(Integer idcampus) {
        this.idcampus = idcampus;
    }

    /**
     * @return the nivel
     */
    public Integer getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the idedicionetudio
     */
    public Integer getIdedicionetudio() {
        return idedicionetudio;
    }

    /**
     * @param idedicionetudio the idedicionetudio to set
     */
    public void setIdedicionetudio(Integer idedicionetudio) {
        this.idedicionetudio = idedicionetudio;
    }

    /**
     * @return the alumnostr
     */
    public String getAlumnostr() {
        return alumnostr;
    }

    /**
     * @param alumnostr the alumnostr to set
     */
    public void setAlumnostr(String alumnostr) {
        this.alumnostr = alumnostr;
    }

    /**
     * @return the cantidadvotos
     */
    public Integer getCantidadvotos() {
        return cantidadvotos;
    }

    /**
     * @param cantidadvotos the cantidadvotos to set
     */
    public void setCantidadvotos(Integer cantidadvotos) {
        this.cantidadvotos = cantidadvotos;
    }
       
}
