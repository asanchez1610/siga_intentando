/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.bean;

import java.util.List;

/**
 *
 * @author AndySanti
 */
public class EleccionAsignatura {
    private List<Integer> elegidos;
    private Boolean eleccion;


    public EleccionAsignatura() {
    }

    public List<Integer> getElegidos() {
        return elegidos;
    }

    public void setElegidos(List<Integer> elegidos) {
        this.elegidos = elegidos;
    }

    public Boolean getEleccion() {
        return eleccion;
    }

    public void setEleccion(Boolean eleccion) {
        this.eleccion = eleccion;
    }
    

}
