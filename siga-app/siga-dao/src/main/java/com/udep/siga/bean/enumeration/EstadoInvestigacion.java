package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonEstadoInvestigacionSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonEstadoInvestigacionSerializer.class)
public enum EstadoInvestigacion {

    REGISTRADO(1, "Registrado"),
    PROCESO_PUBLICACION(2, "En proceso de publicación"),
    PUBLICADO(3, "Publicado en el repositorio");
    
    private final int id;
    private final String nombre;

    EstadoInvestigacion(int id, String nombre) {
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    public static EstadoInvestigacion parse(int id) {
        EstadoInvestigacion estadoInvestigacion = null; // Default
        for (EstadoInvestigacion item : EstadoInvestigacion.values()) {
            if (item.getId() == id) {
                estadoInvestigacion = item;
                break;
            }
        }
        return estadoInvestigacion;
    }
}
