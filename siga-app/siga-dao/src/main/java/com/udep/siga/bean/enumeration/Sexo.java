package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
public enum Sexo {
    MASCULINO(1, "M"),
    FEMENINO(2, "F");
    
    private final int id; 
    private final String codigo; 
    
    Sexo(int id, String codigo){
        this.id = id;
        this.codigo = codigo;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }    

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
    
    public static Sexo parse(int id) {
        Sexo sexo = null; // Default
        for (Sexo item : Sexo.values()) {
            if (item.getId() == id) {
                sexo = item;
                break;
            }
        }
        return sexo;
    }
}
