/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEncuesta;
import com.udep.siga.bean.AsignaturaEncuesta;
import com.udep.siga.dao.LlenarEncuestaDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.BDConstants;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AndySanti
 */
@Repository("llenarencuestaDAO")
public class ILlenarencuestaDAO extends CustomizeJdbcDaoSupport implements LlenarEncuestaDAO{
    @Override
    public boolean checkLlenarencuesta(int idAlumno, int idEdicionEstudio,int idcampus) {
       return this.getJdbcTemplate().queryForObject(BDConstants.FN_CHECK_LLENARENCUESTA, new Object[]{idAlumno,idEdicionEstudio,idcampus}, Boolean.class);
    }
    @Override
    public List<AsignaturaEncuesta> ListadoAsignaturas(int idEdicionEstudio) {
       SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_ASIGNATURAS_ENCUESTA)
                .returningResultSet("resulset", UtilRowMapper.getAsignaturaEncuestaMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDESTUDIO", idEdicionEstudio);
        
        List<AsignaturaEncuesta> resulset =  (List<AsignaturaEncuesta>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset.isEmpty())
            return null;
        return resulset;
    }
    
    public void guardarEleccion(AlumnoEncuesta alumno) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GUARDAR_ASIGNATURAS_ENCUESTA);
          System.out.println( alumno.getIdalumno());
          System.out.println( alumno.getIdestudio());
          System.out.println(alumno.getIdasignatura1());
          System.out.println(alumno.getIdasignatura2());      
                        
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDALUMNO", alumno.getIdalumno())
                .addValue("IDEDICIONESTUDIO", alumno.getIdestudio())
                .addValue("IDASIGNATURA1",alumno.getIdasignatura1())
                .addValue("IDASIGNATURA2", alumno.getIdasignatura2());
             
        try{
            simpleJdbcCall.execute(params);
        }catch(Exception ex){
           System.out.println(ex.getMessage());
        }
    }
    
}
