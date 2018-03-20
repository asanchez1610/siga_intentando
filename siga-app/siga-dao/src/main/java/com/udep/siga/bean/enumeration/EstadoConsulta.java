package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonEstadoConsultaSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonEstadoConsultaSerializer.class)
public enum EstadoConsulta {

    REALIZADO(1, "Realizado"),
    PROCESO(2, "En proceso"),
    RESPONDIDO(3, "Respondido");
    private final int id;
    private final String nombre;

    EstadoConsulta(int id, String nombre) {
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

    public static EstadoConsulta parse(int id) {
        EstadoConsulta estadoConsulta = null; // Default
        for (EstadoConsulta item : EstadoConsulta.values()) {
            if (item.getId() == id) {
                estadoConsulta = item;
                break;
            }
        }
        return estadoConsulta;
    }
}
