package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoMaterialSerializer;

/**
 *
 * @author Wilfredo Atoche
 */
@JsonSerialize(using = JsonTipoMaterialSerializer.class)
public enum TipoMaterial {

    SYLLABUS(1, "Syllabus"),
    SEPARATAS(2, "Separatas"),
    EJERCICIOS(3, "Ejercicios"),
    SOLUCIONES(4, "Soluciones"),
    TRABAJOS(5, "Trabajos"),
    OTROS(6, "Otros"),
    CLASES(7, "Clases"),
    LABORATORIOS(8, "Laboratorios"),
    CASOS(9, "Casos");
    private final int id;
    private final String nombre;

    TipoMaterial(int id, String nombre) {
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

    public static TipoMaterial parse(int id) {
        TipoMaterial tipoMaterial = null; // Default
        for (TipoMaterial item : TipoMaterial.values()) {
            if (item.getId() == id) {
                tipoMaterial = item;
                break;
            }
        }
        return tipoMaterial;
    }
}
