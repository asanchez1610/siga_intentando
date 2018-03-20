package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonEstadoEvaluacionSerializer;

/**
 *
 * @author Wilfredo Atoche
 */
@JsonSerialize(using = JsonEstadoEvaluacionSerializer.class)
public enum EstadoEvaluacion {
    POR_EVALUAR(1, "Por evaluar"),
    EN_CORRECCION(2, "En corrección"),
    ACTA_CERRADA(3, "Acta cerrada"),
    FASE_DE_RECLAMOS(4, "Fase de reclamos"),
    EN_CORRECCION_DE_RECLAMOS(5, "En corrección de reclamos"),
    ACTA_CERRADA_RECLAMOS_CORREGIDOS(6, "Acta cerrada con reclamos corregidos"),
    FASE_DE_RECLAMOS_CERRADA(7, "Fase de reclamos cerrada");
    
    private final int id;
    private final String nombre;

    EstadoEvaluacion(int id, String nombre) {
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

    public static EstadoEvaluacion parse(int id) {
        EstadoEvaluacion estadoEvaluacion = null; // Default
        for (EstadoEvaluacion item : EstadoEvaluacion.values()) {
            if (item.getId() == id) {
                estadoEvaluacion = item;
                break;
            }
        }
        return estadoEvaluacion;
    }
}
