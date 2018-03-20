/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AreaConocimiento;
import com.udep.siga.bean.Especialidad;
import com.udep.siga.bean.EspecialidadAlumnoEstudio;
import com.udep.siga.bean.Estudio;
import com.udep.siga.dao.EspecialidadDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jose Chero Sojo
 */
@Service("especialidadService")
public class EspecialidadService {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EspecialidadDAO especialidadDAO;
    @Autowired
    private CommonsService commonsService;
    
    public boolean checkEspecialidadConfig(int idEdicionEstudio){
        Alumno alumno = usuarioService.getInfoUsuario();
        return especialidadDAO.checkEspecialidadConfig(alumno.getId(), idEdicionEstudio);
    }
    
    public String getEspecialidadConfigMensaje(int idEdicionEstudio){
        Alumno alumno = usuarioService.getInfoUsuario();
        return especialidadDAO.getEspecialidadConfigMensaje(alumno.getId(), idEdicionEstudio);
    }
    
    public boolean checkEspecialidadEleccion(int idEdicionEstudio){
        Alumno alumno = usuarioService.getInfoUsuario();
        return especialidadDAO.checkEspecialidadEleccion(alumno.getId(), idEdicionEstudio);
    }
    
    public Especialidad getEspecialidadByAlumno(int idEdicionEstudio){
        Alumno alumno = usuarioService.getInfoUsuario();
        Especialidad especialidad = especialidadDAO.getEspecialidadByAlumno(alumno.getId(), idEdicionEstudio);
        if( especialidad != null ){
            especialidad.setIdStr(usuarioService.addRefIndirecta(especialidad.getId()));
        }
        return especialidad;
    }
        
    public List<Especialidad> getEspecialidadByPlanEstudio(int idPlanEstudio){
        List<Especialidad> especialidades = especialidadDAO.getEspecialidadByPlanEstudio(idPlanEstudio);
        if( especialidades != null ){
            for (Especialidad especialidad : especialidades){
                especialidad.setIdStr(usuarioService.addRefIndirecta(especialidad.getId()));
            }
        }
        
        return especialidades;
    }
    
    public List<AreaConocimiento> getAreasConocimientoByEspecialidad(int idPlanEstudio, int idEspecialidad, int idEdicionEstudio){
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionEstudio);
        return especialidadDAO.getAreasConocimientoByEspecialidad(idPlanEstudio, idEspecialidad, alumnoEstudio);
    }
    
    public boolean guardarEspecialidadAlumno(int idEdicionEstudio, int idEspecialidad) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionEstudio);
        EspecialidadAlumnoEstudio especialidadAlumno = new EspecialidadAlumnoEstudio(new Especialidad(idEspecialidad), alumnoEstudio);
        return especialidadDAO.guardarEspecialidadAlumno(especialidadAlumno);
    }
}
