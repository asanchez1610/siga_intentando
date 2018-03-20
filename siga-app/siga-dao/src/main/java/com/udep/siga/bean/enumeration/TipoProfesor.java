package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
public enum TipoProfesor {
    TIEMPO_COMPLETO(1, "Tiempo Completo"),
    COLABORADOR(2, "Colaborador"),
    FUTURO_DOCENTE(3, "Futuro Docente"),
    INVITADO(4, "Invitado"),
    A(5, "A");
    
    private final int id; 
    private final String nombre;
    
    TipoProfesor(int id, String nombre){
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
    
    public static TipoProfesor parse(int id) {
        TipoProfesor tipoProfesor = null; // Default
        for (TipoProfesor item : TipoProfesor.values()) {
            if (item.getId() == id) {
                tipoProfesor = item;
                break;
            }
        }
        return tipoProfesor;
    }
}
