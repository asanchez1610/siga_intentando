package com.udep.siga.dao.impl;

import com.udep.siga.bean.Aviso;
import com.udep.siga.bean.Destacado;
import com.udep.siga.bean.Especialidad;
import com.udep.siga.bean.Persona;
import com.udep.siga.dao.AvisoDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.BDConstants;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("avisoDAO")
public class IAvisoDAO extends CustomizeJdbcDaoSupport implements AvisoDAO {

    @Override
    public List<Aviso> getAvisos(int idalumno) {
          SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_AVISOS)
                .returningResultSet("resulset", UtilRowMapper.getAvisoMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDALUMNO", idalumno);
           //     .addValue("ESTUDIOCAMPUS", arrayEstudioCampus);
        
        List<Aviso> resulset =  (List<Aviso>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset.isEmpty())
            return null;
        return resulset;
    }

    @Override
    public Aviso getAviso(int id) {
        String sql = "SELECT TOP 1 A.IDAVISO, A.IDPERSONA, A.TITULO, A.DESCRIPCION, A.FECHA, A.CADUCIDAD, "
                + "A.GENERAL, A.RUTAARCHIVO, A.PARAALUMNOS, A.PARAPROFESORES, A.STICK "
                + "FROM AVISO A WHERE  A.IDAVISO = ?";
        Aviso aviso = (Aviso) this.getJdbcTemplate().queryForObject(sql, new Object[]{id},
                UtilRowMapper.getAvisoMapper());
        return aviso;
    }

    @Override
    public List<Destacado> getDestacado() {
        String sql = "SELECT HTML,ENLACE,VEREN FROM DESTACADO WHERE ACTIVO = 1 "
                + "ORDER BY ID DESC";
        
        List<Destacado> destacados = this.getJdbcTemplate().query(sql, new RowMapper() {
            public Destacado mapRow(ResultSet rs, int rowNum) throws SQLException {
                Destacado destacado = new Destacado();
                destacado.setHtml(rs.getString("HTML"));
                destacado.setEnlace(rs.getString("ENLACE"));
                destacado.setVerEn(rs.getString("VEREN"));
                return destacado;
            }
        });
        
        if (destacados.isEmpty()) {
            return null;
        } else {
            return destacados;
        }
    }

    /*
     * Obtiene total de avisos nuevos en los ultimos 3 dias para mostrar a los alumnos
     */
    public int getTotalAvisosNuevos(String arrayEstudioCampus, String arrayCampus) {

        String sql = "SELECT COUNT(*) "
                + "FROM AVISO A WHERE A.PARAALUMNOS = 1 AND ((A.GENERAL = 0 AND "
                + "EXISTS(SELECT AE.IDAVISO FROM AVISOESTUDIO AE WHERE AE.IDAVISO = A.IDAVISO "
                + "AND (CONVERT(VARCHAR, AE.IDEDICIONESTUDIO) + ',' + CONVERT(VARCHAR, AE.IDCAMPUS)) IN ("
                + arrayEstudioCampus + "))) OR (A.GENERAL = 1 AND EXISTS (SELECT AC.IDAVISO "
                + "FROM AVISOCAMPUS AC WHERE AC.IDAVISO = A.IDAVISO AND AC.IDCAMPUS IN ("
                + arrayCampus + ")))) "
                + "AND DATEDIFF(DAY,GETDATE(),A.CADUCIDAD) >= 0 "
                + "AND DATEDIFF(DAY,A.FECHA,GETDATE()) <= :dias";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dias", AppConstants.RESUMEN_DIAS);

        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
        return count;
    }

    /*
     * Obtiene lista de avisos de asignatura-seccion para mostrar a los alumnos
     */
    public List<Aviso> getAvisoAsignaturaList(int idAsignatura, int idSeccion) {
        String sql = "SELECT A.IDAVISOASIGNATURA,A.IDPROFESOR,A.NOMBRE,A.FECHA, A.DESCRIPCION "
                + "FROM AVISOASIGNATURA A WHERE A.IDASIGNATURADICTADA = :idAsignatura "
                + "AND (A.IDSECCION = :idSeccion OR A.PUBLICO = 1 AND (A.IDPROFESOR IN ( "
                + "SELECT PD.IDPROFESOR FROM PROFESORDICTA PD, PROFESOR P WHERE "
                + "PD.IDPROFESOR=P.IDPROFESOR AND PD.IDASIGNATURADICTADA = :idAsignatura "
                + "AND PD.IDSECCION = :idSeccion ) ) ) ORDER BY A.FECHA DESC ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignatura", idAsignatura);
        params.addValue("idSeccion", idSeccion);

        List<Aviso> avisoList = this.getNamedParameterJdbcTemplate().query(sql, params,
                new RowMapper() {
            public Aviso mapRow(ResultSet rs, int rowNum) throws SQLException {
                Aviso aviso = new Aviso();
                aviso.setId(rs.getInt("IDAVISOASIGNATURA"));
                aviso.setTitulo(rs.getString("NOMBRE"));
                aviso.setPersona(new Persona(rs.getInt("IDPROFESOR")));
                aviso.setFecha(rs.getTimestamp("FECHA"));
                aviso.setDescripcion(rs.getString("DESCRIPCION"));

                return aviso;
            }
        });
        return avisoList;
    }

    /*
     * Obtiene total de avisos nuevos de asignatura-seccion en los ultimos 3 dias 
     * para mostrar a los alumnos
     */
    public int getTotalAvisosAsignaturaNuevos(int idAsignatura, int idSeccion) {

        String sql = "SELECT COUNT(*) "
                + "FROM AVISOASIGNATURA A WHERE A.IDASIGNATURADICTADA = :idAsignatura "
                + "AND (A.IDSECCION = :idSeccion OR A.PUBLICO = 1 AND (A.IDPROFESOR IN ( "
                + "SELECT PD.IDPROFESOR FROM PROFESORDICTA PD, PROFESOR P WHERE "
                + "PD.IDPROFESOR=P.IDPROFESOR AND PD.IDASIGNATURADICTADA = :idAsignatura "
                + "AND PD.IDSECCION = :idSeccion ) ) ) "
                + "AND DATEDIFF(DAY,A.FECHA,GETDATE()) <= :dias";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignatura", idAsignatura);
        params.addValue("idSeccion", idSeccion);
        params.addValue("dias", AppConstants.RESUMEN_DIAS);

        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
        return count;
    }
}
