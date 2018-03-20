package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonEstadoAlumnoSerializer;

/**
 *
 * @author Wilfredo Atoche
 */
/* enum EstadoAlumno */
@JsonSerialize(using = JsonEstadoAlumnoSerializer.class)
public enum EstadoAlumno {
    Alumno(1, "Alumno"),
    Egresado(2, "Egresado"),
    Exalumno(3, "Exalumno"),
    Apto_para_ser_alumno(4, "Apto para ser alumno"),
    Admitidos_con_reserva_de_matrícula(5, "Admitidos con reserva de matrícula"),
    Bachiller_sin_egresar(6, "Bachiller sin egresar");
    
    private final int id; 
    private final String nombre; 
    
    EstadoAlumno(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return this.id;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    public static EstadoAlumno parse(int id) {
        EstadoAlumno estadoAlumno = null; // Default
        for (EstadoAlumno item : EstadoAlumno.values()) {
            if (item.getId() == id) {
                estadoAlumno = item;
                break;
            }
        }
        return estadoAlumno;
    }

    
}
