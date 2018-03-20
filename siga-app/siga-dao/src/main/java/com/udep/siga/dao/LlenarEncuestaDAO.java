/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEncuesta;
import com.udep.siga.bean.AsignaturaEncuesta;
import java.util.List;

/**
 *
 * @author AndySanti
 */
public interface LlenarEncuestaDAO {
    public boolean checkLlenarencuesta(int idAlumno, int idEdicionEstudio,int idcampus);
    public List<AsignaturaEncuesta> ListadoAsignaturas(int idEdicionEstudio);
    public void guardarEleccion(AlumnoEncuesta alumno);
}
