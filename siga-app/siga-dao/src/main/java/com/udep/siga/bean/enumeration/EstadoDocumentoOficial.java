package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonEstadoDocumentoOficialSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonEstadoDocumentoOficialSerializer.class)
public enum EstadoDocumentoOficial {
    SOLICITADO(1, "Solicitado",
    "Pagar el derecho académico y presentar y/o adjuntar el voucher para "
    + "que la solicitud sea autorizada"),
    AUTORIZADO(2, "Autorizado", "Será generado el documento oficial"),
    GENERADO(3, "Generado", "Pendiente de firmas"),
    POR_RECOGER(4, "Por recoger", "Pendiente de ser entregado"),
    ENTREGADO(5, "Entregado", ""),
    POR_CORREGIR(6, "Por corregir", "Pendiente de ser corregido y generado"),
    ELIMINADO(7, "Eliminado", "Regresar a solicitados");
    private final int id;
    private final String nombre;
    private final String pasoSiguiente;

    EstadoDocumentoOficial(int id, String nombre, String pasoSiguiente) {
        this.id = id;
        this.nombre = nombre;
        this.pasoSiguiente = pasoSiguiente;
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

    /**
     * @return the pasoSiguiente
     */
    public String getPasoSiguiente() {
        return pasoSiguiente;
    }

    public static EstadoDocumentoOficial parse(int id) {
        EstadoDocumentoOficial estadoDocumentoOficial = null; // Default
        for (EstadoDocumentoOficial item : EstadoDocumentoOficial.values()) {
            if (item.getId() == id) {
                estadoDocumentoOficial = item;
                break;
            }
        }
        return estadoDocumentoOficial;
    }
}
