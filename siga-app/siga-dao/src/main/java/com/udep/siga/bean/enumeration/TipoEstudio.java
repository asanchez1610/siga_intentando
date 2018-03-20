package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoEstudioSerializer;

/**
 *
 * @author Wilfredo Atoche
 */
/* enum TipoEstudio */
@JsonSerialize(using = JsonTipoEstudioSerializer.class)
public enum TipoEstudio {
    Pregrado(1, "Pregrado"),
    Maestría(2, "Maestría"),
    Técnico(3, "Técnico"),
    Diplomado(4, "Diplomado"),
    Bachiller_de_pregrado(5, "Bachiller de pregrado"),
    Propedeútico_Pregrado(6, "Propedeútico Pregrado"),
    Nivelación_Pregrado(7, "Nivelación Pregrado"),
    Pregrado_Especial(8, "Pregrado Especial");
    
    private final int id; 
    private final String nombre;
    
    TipoEstudio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return this.id;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    public static TipoEstudio parse(int id) {
        TipoEstudio tipoEstudio = null; // Default
        for (TipoEstudio item : TipoEstudio.values()) {
            if (item.getId() == id) {
                tipoEstudio = item;
                break;
            }
        }
        return tipoEstudio;
    }
}
