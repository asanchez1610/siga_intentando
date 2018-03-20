/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AreaConocimiento;
import com.udep.siga.bean.Especialidad;
import com.udep.siga.bean.EspecialidadAlumnoEstudio;
import com.udep.siga.bean.EspecialidadAsignatura;
import com.udep.siga.bean.EspecialidadConfiguracion;
import com.udep.siga.bean.PlanEstudioAsignatura;
import com.udep.siga.dao.AlumnoEstudioDAO;
import com.udep.siga.dao.EspecialidadDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.BDConstants;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 *
 * @author Jose Chero Sojo
 */
@Repository("especialidadDAO")
public class IEspecialidadDAO extends CustomizeJdbcDaoSupport implements EspecialidadDAO{
    
    @Autowired
    private AlumnoEstudioDAO alumnoEstudioDAO;
    
    @Override
    public boolean guardarEspecialidad(Especialidad especialidad) {
        return false;
    }

    @Override
    public boolean guardarEspecialidadConfiguracion(EspecialidadConfiguracion configuracion) {
        return false;
    }

    @Override
    public boolean guardarEspecialidadAsignatura(EspecialidadAsignatura especialidadAsignatura) {
        return false;
    }

    @Override
    public boolean guardarEspecialidadAlumno(EspecialidadAlumnoEstudio especialidadAlumno) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_REGISTRAR_ESPECIALIDADBYALUMNO)
                .declareParameters(new SqlOutParameter("RESP", Types.INTEGER));
                        
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDESPECIALIDAD", especialidadAlumno.getEspecialidad().getId())
                .addValue("IDALUMNO", especialidadAlumno.getAlumnoEstudio().getAlumno().getId())
                .addValue("IDEDICIONESTUDIO", especialidadAlumno.getAlumnoEstudio().getEdicionestudio().getId())
                .addValue("IDPERSONAREGISTRO", especialidadAlumno.getAlumnoEstudio().getAlumno().getId());
             
        try{
            return simpleJdbcCall.executeObject(Integer.class, params) > 0;
        }catch(Exception ex){
            return false;
        }
    }
    
    private List<Map> getResulsetEspecialidadConfig(int idAlumno, int idEdicionEstudio){
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_ACTIVA_SELECC_ESPEC_ALUMNO)
                .returningResultSet("resulset", new ColumnMapRowMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDALUMNO", idAlumno)
                .addValue("IDEDICIONESTUDIO", idEdicionEstudio);
        
        List<Map> resulset = (List<Map>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset == null || resulset.isEmpty())
            return null; 
        return resulset;
    }

    @Override
    public boolean checkEspecialidadConfig(int idAlumno, int idEdicionEstudio) {
        List<Map> resulset = this.getResulsetEspecialidadConfig(idAlumno, idEdicionEstudio);
        
        if(resulset != null){
            if( !(Boolean) resulset.get(0).get("TIENEESPECIALIDAD") ){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getEspecialidadConfigMensaje(int idAlumno, int idEdicionEstudio){
        List<Map> resulset = this.getResulsetEspecialidadConfig(idAlumno, idEdicionEstudio);
        
        if(resulset != null)
            return (String) resulset.get(0).get("MENSAJE");        
        return null;
    }

    @Override
    public boolean checkEspecialidadEleccion(int idAlumno, int idEdicionEstudio) {
        return this.getJdbcTemplate().queryForObject(BDConstants.FN_CHECK_ESPECIALIDADALUMNOESTUDIO, new Object[]{idAlumno,idEdicionEstudio}, Boolean.class);
    }

    @Override
    public Especialidad getEspecialidadByAlumno(int idAlumno, int idEdicionEstudio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_ESPECIALIDAD_BYALUMNO)
                .returningResultSet("resulset", UtilRowMapper.getEspecialidadMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDALUMNO", idAlumno)
                .addValue("IDEDICIONESTUDIO", idEdicionEstudio);
        
        List<Object> resulset =  (List<Object>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset.isEmpty())
            return null;
        return (Especialidad) resulset.get(0);
    }

    @Override
    public List<Especialidad> getEspecialidadByPlanEstudio(int idPlanEstudio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_ESPECIALIDADES_BYPLANESTUDIO)
                .returningResultSet("resulset", UtilRowMapper.getEspecialidadMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDPLANESTUDIOS", idPlanEstudio);
        
        List<Especialidad> resulset =  (List<Especialidad>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset.isEmpty())
            return null;
        return resulset;
    }
    
    @Override
    public List<AreaConocimiento> getAreasConocimientoByEspecialidad(int idPlanEstudio, int idEspecialidad, AlumnoEstudio alumnoEstudio){
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_AREASCONOCIMIENTO_BYIDESPECIALIDAD)
                .returningResultSet("resulset", UtilRowMapper.getAreaConocimientoMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDPLANESTUDIOS", idPlanEstudio)
                .addValue("IDESPECIALIDAD", idEspecialidad);
        
        List<AreaConocimiento> resulset = (List<AreaConocimiento>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset.isEmpty()){
            return null;
        }
        
        for(AreaConocimiento areac : resulset){
            areac.setPlanEstudioAsignaturaList(this.findAsignaturas(idPlanEstudio, idEspecialidad, areac.getId(), alumnoEstudio));
        }
        
        return resulset;
    }
    
    private List<PlanEstudioAsignatura> findAsignaturas(int idPlanEstudio, int idEspecialidad, int idAreaConocimiento, AlumnoEstudio alumnoEstudio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_ASIGNATURAS_BYESPECIALIDAD_AC)
                .returningResultSet("resulset", UtilRowMapper.getPlanEstudioAsignaturaCustomMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDPLANESTUDIOS", idPlanEstudio)
                .addValue("IDESPECIALIDAD", idEspecialidad)
                .addValue("IDAREACONOCIMIENTO", idAreaConocimiento);
        
        List<PlanEstudioAsignatura> resulset = (List<PlanEstudioAsignatura>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset.isEmpty()){
            return null;
        }
        
        for(PlanEstudioAsignatura plan : resulset){
            plan.setRequisitosList(alumnoEstudioDAO.getRequisitosByPlanEstudioAsignatura(plan));
            plan.setEstadoEnMalla(alumnoEstudioDAO.getEstadoAsignaturaAlumnoPlanEstudio(idPlanEstudio, alumnoEstudio, plan.getAsignatura().getId()));

            if (plan.getEstadoEnMalla() == 4 || plan.getEstadoEnMalla() == 9) {
                plan.setNotaHistorial(alumnoEstudioDAO.getNotaHistorial(alumnoEstudio, plan.getAsignatura().getId(), idPlanEstudio));
            }
        }
        
        return resulset;
    }
}
