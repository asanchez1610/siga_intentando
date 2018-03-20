package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonEstadoSolicitudSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonEstadoSolicitudSerializer.class)
public enum EstadoSolicitud {

    POR_REVISAR(1, "Por revisar"),
    EN_EVALUACION(2, "En evaluación (Consejo de Facultad)"),
    POR_RESPONDER(3, "Por responder al alumno"),
    SOLICITUD_FINALIZADA(4, "Solicitud Finalizada"),
    POR_REVISAR_2(5, "Por revisar*");
    
    private final int id;
    private final String nombre;

    EstadoSolicitud(int id, String nombre) {
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

    public static EstadoSolicitud parse(int id) {
        EstadoSolicitud estadoSolicitud = null; // Default
        for (EstadoSolicitud item : EstadoSolicitud.values()) {
            if (item.getId() == id) {
                estadoSolicitud = item;
                break;
            }
        }
        return estadoSolicitud;
    }
}
