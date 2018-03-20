/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.bean;

/**
 *
 * @author AndySanti
 */
public class AlumnoEncuesta {

    private int idencuesta;
    private int idalumno;
    private int idestudio;
    private int idasignatura1;
    private int idasignatura2;

    public AlumnoEncuesta() {
    }

    public AlumnoEncuesta(int idencuesta, int idalumno, int idestudio, int idasignatura1, int idasignatura2) {
        this.idencuesta = idencuesta;
        this.idalumno = idalumno;
        this.idestudio = idestudio;
        this.idasignatura1 = idasignatura1;
        this.idasignatura2 = idasignatura2;
    }

    public int getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(int idencuesta) {
        this.idencuesta = idencuesta;
    }

    public int getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(int idalumno) {
        this.idalumno = idalumno;
    }

    public int getIdestudio() {
        return idestudio;
    }

    public void setIdestudio(int idestudio) {
        this.idestudio = idestudio;
    }

    public int getIdasignatura1() {
        return idasignatura1;
    }

    public void setIdasignatura1(int idasignatura1) {
        this.idasignatura1 = idasignatura1;
    }

    public int getIdasignatura2() {
        return idasignatura2;
    }

    public void setIdasignatura2(int idasignatura2) {
        this.idasignatura2 = idasignatura2;
    }

}
