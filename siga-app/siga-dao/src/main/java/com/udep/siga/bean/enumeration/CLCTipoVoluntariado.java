package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonCLCTipoVoluntariadoSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonCLCTipoVoluntariadoSerializer.class)
public enum CLCTipoVoluntariado {
    VOLUNTARIADO_ASISTENCIAL(1,"Voluntariado Asistencial"),
    VOLUNTARIADO_PROFESIONAL(2,"Voluntariado Profesional");
    
    private int id; 
    private String nombre; 
    
    CLCTipoVoluntariado(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    
    public static CLCTipoVoluntariado parse(int id) {
        CLCTipoVoluntariado clcTipoVoluntariado = null; // Default
        for (CLCTipoVoluntariado item : CLCTipoVoluntariado.values()) {
            if (item.getId() == id) {
                clcTipoVoluntariado = item;
                break;
            }
        }
        return clcTipoVoluntariado;
    }
    
}
