package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Solicitud;
import com.udep.siga.bean.TipoSolicitud;
import com.udep.siga.bean.enumeration.CategoriaSolicitud;
import com.udep.siga.bean.enumeration.EstadoSolicitud;
import com.udep.siga.dao.TramiteAcademicoDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.Rol;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("tramiteAcademicoDAO")
public class ITramiteAcademico extends CustomizeJdbcDaoSupport implements TramiteAcademicoDAO {

    @Override
    public List<Solicitud> getPendientes(int idalumno, int idEdicionestudio) {
        String sql = "SELECT S.IDSOLICITUDACTUALIZACION, S.FECHA, S.IDESTADOSOLICITUDACTUALIZACION, "
                + "S.IDTIPOSOLICITUDACTUALIZACION, T.NOMBRE, S.APROBADA, S.OBSALUMNO, S.RESPUESTA, "
                + "S.FECHACONFIRMACION FROM SOLICITUDACTUALIZACION S, TIPOSOLICITUDACTUALIZACION T "
                + "WHERE S.IDTIPOSOLICITUDACTUALIZACION = T.IDTIPOSOLICITUDACTUALIZACION AND "
                + "S.IDALUMNO = :idAlumno AND S.IDESTADOSOLICITUDACTUALIZACION <> :estado "
                + "AND S.IDEDICIONESTUDIO = :idEdicionestudio ORDER BY S.FECHA DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idalumno);
        params.addValue("idEdicionestudio", idEdicionestudio);
        params.addValue("estado", EstadoSolicitud.SOLICITUD_FINALIZADA.getId());

        List<Solicitud> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getTramiteAcademicoMapper());
        return list;
    }

    @Override
    public List<Solicitud> getFinalizados(int idalumno, int idEdicionestudio) {
        String sql = "SELECT S.IDSOLICITUDACTUALIZACION,S.FECHA,S.IDESTADOSOLICITUDACTUALIZACION, "
                + "S.IDTIPOSOLICITUDACTUALIZACION, T.NOMBRE, S.APROBADA, S.OBSALUMNO, S.RESPUESTA, "
                + "S.FECHACONFIRMACION FROM SOLICITUDACTUALIZACION S, TIPOSOLICITUDACTUALIZACION T "
                + "WHERE S.IDTIPOSOLICITUDACTUALIZACION=T.IDTIPOSOLICITUDACTUALIZACION AND "
                + "S.IDALUMNO = :idAlumno AND S.IDESTADOSOLICITUDACTUALIZACION = :estado "
                + "AND S.IDEDICIONESTUDIO = :idEdicionestudio ORDER BY S.FECHA DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idalumno);
        params.addValue("idEdicionestudio", idEdicionestudio);
        params.addValue("estado", EstadoSolicitud.SOLICITUD_FINALIZADA.getId());

        List<Solicitud> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getTramiteAcademicoMapper());
        return list;
    }

    @Override
    public List<TipoSolicitud> getTipoSolicitudesByCategoria(
            CategoriaSolicitud categoriaSolicitud, List<Rol> rolList) {

        String sql = "SELECT IDTIPOSOLICITUDACTUALIZACION, NOMBRE "
                + "FROM TIPOSOLICITUDACTUALIZACION WHERE TIPO = :tipo AND "
                + "(FECHAVENCIMIENTO IS NULL OR FECHAVENCIMIENTO >= GETDATE()) ";

        if (rolList.contains(Rol.ALUMNO_POSGRADO)  && !rolList.contains(Rol.ALUMNO_PREGRADO)) {
            sql += "AND IDTIPOSOLICITUDACTUALIZACION NOT IN (25,27,10,20,21,22,30,13,19) ";
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tipo", categoriaSolicitud.getId());

        List<TipoSolicitud> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getTipoSolicitudMapper());
        return list;
    }

    @Override
    public void saveTramiteAcademico(Solicitud solicitud, AlumnoEstudio alumnoEstudio) {
        String sql = "INSERT INTO SOLICITUDACTUALIZACION(IDPERIODOACADEMICO,IDCAMPUS,IDCENTROACADEMICO, "
                + "IDESTADOSOLICITUDACTUALIZACION,IDTIPOSOLICITUDACTUALIZACION,IDEDICIONESTUDIO, "
                + "IDALUMNO, FECHA, APROBADA, PAGADA, OBSALUMNO) "
                + "VALUES (:idPeriodoAcademico, :idCampus, :idCentroacademico,:estado, :idTipo, "
                + ":idEdicionEstudio, :idAlumno, GETDATE(), 0, 0, :obsAlumno) ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idTipo", solicitud.getTipoSolicitud().getId());
        params.addValue("obsAlumno", solicitud.getObsAlumno());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("idPeriodoAcademico", alumnoEstudio.getPeriodoAcademicoVigente().getId());
        params.addValue("idCampus", alumnoEstudio.getCampus().getId());
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("estado", EstadoSolicitud.POR_REVISAR.getId());
        params.addValue("idCentroacademico", alumnoEstudio.getEdicionestudio()
                .getEstudio().getCentroAcademico().getId());

        this.getNamedParameterJdbcTemplate().update(sql, params);
    }
    
    public int getTotalRespuestasNuevas(int idAlumno) {

        String sql = "SELECT COUNT(*) FROM SOLICITUDACTUALIZACION S, TIPOSOLICITUDACTUALIZACION T "
                + "WHERE S.IDTIPOSOLICITUDACTUALIZACION=T.IDTIPOSOLICITUDACTUALIZACION "
                + "AND S.IDALUMNO = :idAlumno AND S.RESPUESTA IS NOT NULL "
                + "AND (DATEDIFF(DAY,S.FECHACONFIRMACION,GETDATE()) <= :dias  "
                + "OR S.FECHACONFIRMACION IS NULL) AND DATEDIFF(YEAR,S.FECHA,GETDATE()) = 0 ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("dias", AppConstants.RESUMEN_DIAS);

        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
        return count;
    }
}
