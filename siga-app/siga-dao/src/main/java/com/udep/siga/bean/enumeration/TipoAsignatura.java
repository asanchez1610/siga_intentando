package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoAsignaturaSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonTipoAsignaturaSerializer.class)
public enum TipoAsignatura {

    OBLIGATORIA(1, "Obligatoria", "Obl", "Créditos Obligatorios"),
    ELECTIVA(2, "Electiva", "Ele", "Créditos Electivos"),
    LIBRE_CONFIGURACION(3, "Libre Configuracion", "LC", "Créditos de Libre Configuración"),
    ALTERNATIVA(4, "Alternativa", "Alt", "Créditos Alternativos"),
    MENCION(5, "Mencion", "Mc", "Créditos de especialización"),
    MINORS(6, "Créditos Minors", "MIN", "Créditos Minors"),
    OPTATIVO(7, "Créditos Optativos", "OPT", "Créditos Optativos");
    
    private final int id;
    private final String descripcion;    
    private final String sigla;
    private final String creditoDescripcion;

    TipoAsignatura(int id, String descripcion, String sigla, String creditoDescripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.sigla = sigla;
        this.creditoDescripcion = creditoDescripcion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    public static TipoAsignatura parse(int id) {
        TipoAsignatura tipoAsignatura = null; // Default
        for (TipoAsignatura item : TipoAsignatura.values()) {
            if (item.getId() == id) {
                tipoAsignatura = item;
                break;
            }
        }
        return tipoAsignatura;
    }

    /**
     * @return the creditoDescripcion
     */
    public String getCreditoDescripcion() {
        return creditoDescripcion;
    }
}
