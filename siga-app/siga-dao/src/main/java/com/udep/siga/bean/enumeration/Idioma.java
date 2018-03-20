package com.udep.siga.bean.enumeration;

/**
 *
 * @author Administrador
 */
public enum Idioma {

    ESPANOL(1, "Español"),
    INGLES(2, "Inglés");
    private final int id;
    private final String nombre;

    Idioma(int id, String nombre) {
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
    
    public static Idioma parse(int id) {
        Idioma idioma = null; // Default
        for (Idioma item : Idioma.values()) {
            if (item.getId() == id) {
                idioma = item;
                break;
            }
        }
        return idioma;
    }
}
