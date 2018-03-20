package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoArticuloSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonTipoArticuloSerializer.class)
public enum TipoArticulo {
    ARTICULO(1, "Artículo"),
    ARTICULO_REVISION(2, "Artículo de revisión"),
    ARTICULO_CONGRESO(3, "Artículo de congreso"),
    ESTUDIO_BREVE(4, "Estudio breve"),
    COMUNICACION_CORTA(5, "Comunicación corta"),
    CARTA(6, "Correspondencia, Carta"),
    DISCUSION(7, "Discusión"),
    RESENIA_LIBROS(8, "Reseña de libros"),
    RESENIA_PRODUCTOS(9, "Reseña de productos"),
    Editorial(10, "Editorial");
    
    private final int id;
    private final String nombre;

    TipoArticulo(int id, String nombre) {
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
    
    public static TipoArticulo parse(int id) {
        TipoArticulo tipoArticulo = null; // Default
        for (TipoArticulo item : TipoArticulo.values()) {
            if (item.getId() == id) {
                tipoArticulo = item;
                break;
            }
        }
        return tipoArticulo;
    }
}
