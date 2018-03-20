package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
public enum TipoEvaluacion {
    PRACTICA(1, "Práctica"),
    EXAMEN(2, "Examen"),
    CONTROL(3, "Control"),
    TRABAJO(4, "Trabajo"),
    LABORATORIO(5, "Laboratorio"),
    PARTICIPACIÓN(6, "Participación"),
    SUSTITUTORIO_APLAZADO(7, "Sustitutorio/Aplazado");
    
    private final int id;
    private final String nombre;

    TipoEvaluacion(int id, String nombre) {
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

    public static TipoEvaluacion parse(int id) {
        TipoEvaluacion tipoEvaluacion = null; // Default
        for (TipoEvaluacion item : TipoEvaluacion.values()) {
            if (item.getId() == id) {
                tipoEvaluacion = item;
                break;
            }
        }
        return tipoEvaluacion;
    }
}
