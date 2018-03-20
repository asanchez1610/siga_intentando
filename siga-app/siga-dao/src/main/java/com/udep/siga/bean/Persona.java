package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoDocIdentidad;
import com.udep.siga.bean.enumeration.Sexo;
import com.udep.siga.util.AppConstants;
import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class Persona implements Serializable{

    private int id;
    private TipoDocIdentidad tipoDocIdentidad;
    private Sexo sexo;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String foto;
    private boolean asesor;
    /* Utiles */
    private PeriodoAcademico periodoAcademicoAsesor;

    public Persona() {
    }

    public Persona(int id) {
        this.id = id;
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
     * @return the tipoDocIdentidad
     */
    public TipoDocIdentidad getTipoDocIdentidad() {
        return tipoDocIdentidad;
    }

    /**
     * @param tipoDocIdentidad the tipoDocIdentidad to set
     */
    public void setTipoDocIdentidad(TipoDocIdentidad tipoDocIdentidad) {
        this.tipoDocIdentidad = tipoDocIdentidad;
    }

    /**
     * @return the sexo
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidoPaterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * @param apellidoPaterno the apellidoPaterno to set
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * @return the apellidoMaterno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * @param apellidoMaterno the apellidoMaterno to set
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        if (this.dni == null) {
            return "";
        }
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
        if (foto == null) {
            if (this.getSexo() == null) {
                this.setSexo(Sexo.MASCULINO);
            }
            if (this.getSexo().getId() == Sexo.MASCULINO.getId()) {
                return AppConstants.PATH_FOTO_MASCULINO_DEFAULT;
            } else {
                if (this.getSexo().getId() == Sexo.FEMENINO.getId()) {
                    return AppConstants.PATH_FOTO_FEMENINO_DEFAULT;
                }
            }

        }
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * @return the asesor
     */
    public boolean isAsesor() {
        return asesor;
    }

    /**
     * @param asesor the asesor to set
     */
    public void setAsesor(boolean asesor) {
        this.asesor = asesor;
    }

    /**
     * @return the periodoAcademicoAsesor
     */
    public PeriodoAcademico getPeriodoAcademicoAsesor() {
        return periodoAcademicoAsesor;
    }

    /**
     * @param periodoAcademicoAsesor the periodoAcademicoAsesor to set
     */
    public void setPeriodoAcademicoAsesor(PeriodoAcademico periodoAcademicoAsesor) {
        this.periodoAcademicoAsesor = periodoAcademicoAsesor;
    }
}
