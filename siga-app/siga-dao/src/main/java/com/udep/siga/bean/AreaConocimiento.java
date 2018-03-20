package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public class AreaConocimiento implements Serializable{
    private int id;
    private String nombre;
    private List<PlanEstudioAsignatura> planEstudioAsignaturaList;
    
    public AreaConocimiento(){}

    public AreaConocimiento(int id){
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   
    /**
     * @return the planEstudioAsignaturaList
     */
    public List<PlanEstudioAsignatura> getPlanEstudioAsignaturaList() {
        return planEstudioAsignaturaList;
    }

    /**
     * @param planEstudioAsignaturaList the planEstudioAsignaturaList to set
     */
    public void setPlanEstudioAsignaturaList(List<PlanEstudioAsignatura> planEstudioAsignaturaList) {
        this.planEstudioAsignaturaList = planEstudioAsignaturaList;
    }
}
