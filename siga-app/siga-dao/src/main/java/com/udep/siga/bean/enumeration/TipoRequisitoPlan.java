package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoRequisitoPlanSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonTipoRequisitoPlanSerializer.class)
public enum TipoRequisitoPlan {
    
    CREDITOS_TOTALES(1, "Creditos Totales"),
    NIVEL_INGLES(2, "Nivel de Ingles"),
    HORAS_PRACTICA_PREPROFESIONAL(3, "Horas de practicas preprofesionales"),
    OFIMATICA_BASICA(4, "Ofimatica Basica"),
    OFIMATICA_AVANZAADA(5, "Ofimatica Avanzada"),
    MANEJO_SOFTWARE(6, "Manejo de Software"),
    /* AGREGAR NUEVOS TIPOS DE REQUISITO_PLAN  ECONOMIA 2014 */
 //   HORAS_VOLUNTARIADO(7, "Horas de Voluntariado"),
    EXAMEN_COMPRENSIVO_MICROECONOMIA(7,"Examen Comprensivo de Microeconomía"),
    EXAMEN_COMPRENSIVO_MACROECONOMIA(8,"Examen Comprensivo de Macroeconomía");
    
    private final int id;
    private final String descripcion;

    TipoRequisitoPlan(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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
    public static TipoRequisitoPlan parse(int id) {
        TipoRequisitoPlan tipoRequisitoPlan = null; // Default
        for (TipoRequisitoPlan item : TipoRequisitoPlan.values()) {
            if (item.getId() == id) {
                tipoRequisitoPlan = item;
                break;
            }
        }
        return tipoRequisitoPlan;
    }
}
