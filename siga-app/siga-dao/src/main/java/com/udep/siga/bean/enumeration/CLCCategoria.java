package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonCLCCategoriaSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonCLCCategoriaSerializer.class)
public enum CLCCategoria {
    
    ACTIVIDAD_ARTISTICA_DESTACADA(1, "CLC_CAT_ACTARTDEST"),
    ACTIVIDAD_INVESTIGACION(2,"CLC_CAT_ACTINVEST"),
    PROYECTO_SOCIAL_ASISTENCIAL(3,""),
    PROYECTO_SOCIAL_PROFESIONAL(4,""),
    ACTIVIDAD_EXTRACURRICULAR_CONVENIO(5,"CLC_CAT_ACTEXTRACONVENIO"),
    ASIGNATURA_PROGRAMA_DISNEY(6,"CLC_CAT_PROGDISNEY"),
    HORAS_CAPACITACION_PROFESIONAL(7,"CLC_CAT_HORASCAPACPROF"),
    HORAS_ESTUDIO_DIRIGIDO(8,"CLC_CAT_HORASESTDIRIGIDO"),
    HORAS_PRACTICA_PREPROFESIONALES(9,"CLC_CAT_PRACTPREPROFESIONAL"),
    NIVEL_BASICO_TERCER_IDIOMA(10,""),
    INTERMEDIO_TERCER_IDIOMA(11,""),
    NIVEL_BASICO_CUARTO_IDIOMA(12,""),
    ACTIVIDAD_DEPORTIVA_DESTACADA(13,""),
    ASIGNATURA_INTERCAMBIO_ESTUDIANTIL(14,"");
        
    private final int id;
    private final String tabla;

    CLCCategoria(int id, String tabla) {
        this.id = id;
        this.tabla = tabla;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the tabla
     */
    public String getTabla() {
        return tabla;
    }
}
