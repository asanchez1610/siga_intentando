package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonDiaSerializer;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Wilfredo Atoche
 */
/* enum Dia */
@JsonSerialize(using = JsonDiaSerializer.class)
public enum Dia {
    Lunes(1,"Lun"),
    Martes(2,"Mar"),
    Miércoles(3,"Mié"),
    Jueves(4,"Jue"),
    Viernes(5,"Vie"),
    Sábado(6,"Sáb");
    
    private int id; 
    private String sigla; 
    
    Dia(int id, String sigla) {
        this.id = id;
        this.sigla = sigla;
    }

    public int getId() {
        return this.id;
    }
    
    public String getSigla() {
        return this.sigla;
    }
    
    public static Dia parse(int id) {
        Dia dia = null; // Default
        for (Dia item : Dia.values()) {
            if (item.getId() == id) {
                dia = item;
                break;
            }
        }
        return dia;
    }
    
    @Override
    public String toString() {
         return org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}