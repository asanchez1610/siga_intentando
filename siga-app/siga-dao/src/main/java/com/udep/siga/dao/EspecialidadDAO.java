/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AreaConocimiento;
import com.udep.siga.bean.Especialidad;
import com.udep.siga.bean.EspecialidadAlumnoEstudio;
import com.udep.siga.bean.EspecialidadAsignatura;
import com.udep.siga.bean.EspecialidadConfiguracion;
import java.util.List;

/**
 *
 * @author Jose Chero Sojo
 */
public interface EspecialidadDAO {
    public boolean guardarEspecialidad(Especialidad especialidad);
    public boolean guardarEspecialidadConfiguracion(EspecialidadConfiguracion configuracion);
    public boolean guardarEspecialidadAsignatura(EspecialidadAsignatura especialidadAsignatura);
    public boolean guardarEspecialidadAlumno(EspecialidadAlumnoEstudio especialidadAlumno);
    public boolean checkEspecialidadConfig(int idAlumno, int idEdicionEstudio);
    public String getEspecialidadConfigMensaje(int idAlumno, int idEdicionEstudio);
    public boolean checkEspecialidadEleccion(int idAlumno, int idEdicionEstudio);
    public Especialidad getEspecialidadByAlumno(int idAlumno, int idEdicionEstudio);
    public List<Especialidad> getEspecialidadByPlanEstudio(int idPlanEstudio);
    public List<AreaConocimiento> getAreasConocimientoByEspecialidad(int idPlanEstudio, int idEspecialidad, AlumnoEstudio alumnoEstudio);
}