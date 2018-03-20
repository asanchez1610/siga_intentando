/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEncuesta;
import com.udep.siga.bean.AsignaturaEncuesta;
import com.udep.siga.bean.EleccionAsignatura;
import com.udep.siga.dao.LlenarEncuestaDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AndySanti
 */
@Service("llenarencuestaService")
public class LlenarEncuestaService {
   
    @Autowired
    private UsuarioService usuarioService; 
    @Autowired
    private LlenarEncuestaDAO llenarencuesta;
    
    public boolean checkEspecialidadConfig(int idEdicionEstudio){
        Alumno alumno = usuarioService.getInfoUsuario();
        int idcampus =alumno.getAlumnoEstudioList().get(0).getCampus().getId();
        return llenarencuesta.checkLlenarencuesta(alumno.getId(), idEdicionEstudio,idcampus);
    }
    
    public List<AsignaturaEncuesta> ListaAsignaturas(int idEdicionEstudio){
        
        return llenarencuesta.ListadoAsignaturas(idEdicionEstudio);
    }
    public void GuardarEncuesta(AlumnoEncuesta alumno){
       
        llenarencuesta.guardarEleccion(alumno);
    }
}
