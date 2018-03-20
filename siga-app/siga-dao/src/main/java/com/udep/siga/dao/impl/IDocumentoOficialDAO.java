package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Solicitud;
import com.udep.siga.bean.TipoSolicitud;
import com.udep.siga.bean.enumeration.EstadoDocumentoOficial;
import com.udep.siga.dao.DocumentoOficialDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.Rol;
import java.util.Calendar;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrador
 */
@Repository("documentoOficialDAO")
public class IDocumentoOficialDAO extends CustomizeJdbcDaoSupport implements DocumentoOficialDAO {

    @Override
    public List<Solicitud> getPendientes(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT S.IDSOLICITUD,S.IDTIPODOCOFICIAL,T.NOMBRE,FECHASOLICITUD,VOUCHER, "
                + "S.IDESTADOSOLICITUD,S.OBSSOLICITUD,S.OBSSECRETARIA,NROSOLICITUD,"
                + "S.IDCAMPUSENTREGA,S.IDIOMA FROM SOLICITUD S,TIPODOCOFICIAL T "
                + "WHERE S.IDTIPODOCOFICIAL = T.IDTIPODOCOFICIAL AND S.IDALUMNO = :idAlumno "
                + "AND S.IDEDICIONESTUDIO = :edicionEstudio AND S.IDESTADOSOLICITUD != :estado "
                + "AND S.IDESTADOSOLICITUD != :estadoEliminado "
                + "ORDER BY S.IDESTADOSOLICITUD, S.FECHASOLICITUD ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("edicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("estado", EstadoDocumentoOficial.ENTREGADO.getId());
        params.addValue("estadoEliminado", EstadoDocumentoOficial.ELIMINADO.getId());

        List<Solicitud> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getDocumentoOficialMapper());
        return list;
    }

    @Override
    public List<Solicitud> getFinalizados(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT S.IDSOLICITUD, S.IDTIPODOCOFICIAL, T.NOMBRE, FECHASOLICITUD, VOUCHER, "
                + "S.IDESTADOSOLICITUD, S.OBSSOLICITUD, S.OBSSECRETARIA, NROSOLICITUD, "
                + "S.IDCAMPUSENTREGA,S.IDIOMA FROM SOLICITUD S,TIPODOCOFICIAL T "
                + "WHERE S.IDTIPODOCOFICIAL = T.IDTIPODOCOFICIAL AND S.IDALUMNO = :idAlumno "
                + "AND S.IDEDICIONESTUDIO = :idEdicionestudio AND S.IDESTADOSOLICITUD = :estado "
                + "AND S.IDESTADOSOLICITUD != :estadoEliminado "
                + "ORDER BY S.IDESTADOSOLICITUD, S.FECHASOLICITUD ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionestudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("estado", EstadoDocumentoOficial.ENTREGADO.getId());
        params.addValue("estadoEliminado", EstadoDocumentoOficial.ELIMINADO.getId());

        List<Solicitud> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getDocumentoOficialMapper());
        return list;
    }

    @Override
    public String getVoucher(int idSolicitud) {
        String sqlCount = "SELECT COUNT(*) FROM ARCHIVOSOLICITUD A WHERE A.VOUCHER = 1 AND A.IDSOLICITUD = ?";
        int existsVoucher = this.getJdbcTemplate().queryForObject(sqlCount, new Object[]{idSolicitud}, Integer.class);
        if(existsVoucher > 0){
            String sql = "SELECT TOP 1 RUTAARCHIVO FROM ARCHIVOSOLICITUD "
                    + "WHERE IDSOLICITUD = :idsolicitud AND VOUCHER = 1 "
                    + "ORDER BY FECHAREGISTRO DESC";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idsolicitud", idSolicitud);

            String ruta = this.getNamedParameterJdbcTemplate().queryForObject(sql, params,
                    String.class);
            return ruta;
        }
        return "";
    }

    @Override
    @Transactional
    public void saveVoucher(int idSolicitud, String pathFile) {
        String sql = "SELECT COUNT(*) FROM ARCHIVOSOLICITUD A WHERE A.VOUCHER = 1 AND A.IDSOLICITUD = ?";
        int existsVoucher = this.getJdbcTemplate().queryForObject(sql, new Object[]{idSolicitud}, Integer.class);
        if (existsVoucher > 0) {
            sql = "UPDATE ARCHIVOSOLICITUD SET RUTAARCHIVO = :ruta, FECHAREGISTRO = GETDATE() "
                    + "WHERE VOUCHER = 1 AND IDSOLICITUD = :idsolicitud";
        } else {
            sql = "INSERT INTO ARCHIVOSOLICITUD (IDSOLICITUD,VOUCHER,RUTAARCHIVO,OFICIAL, "
                    + "FECHAREGISTRO) VALUES(:idsolicitud, 1, :ruta,0, GETDATE())";
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idsolicitud", idSolicitud);
        params.addValue("ruta", pathFile);

        int rowsAffected = this.getNamedParameterJdbcTemplate().update(sql, params);

        if (rowsAffected > 0) {
            sql = "UPDATE SOLICITUD SET VOUCHER = :voucher "
                    + "WHERE IDSOLICITUD = :id";
            params = new MapSqlParameterSource();
            params.addValue("voucher", true);
            params.addValue("id", idSolicitud);

            this.getNamedParameterJdbcTemplate().update(sql, params);
        }
    }

    @Override
    public String getSiglaTipoDocOficial(int idTipo) {
        String sql = "SELECT TOP 1 SIGLA FROM TIPODOCOFICIAL "
                + "WHERE IDTIPODOCOFICIAL = :tipo ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tipo", idTipo);

        return this.getNamedParameterJdbcTemplate().queryForObject(sql, params,
                String.class);
    }

    @Override
    public Solicitud generarCodigos(Solicitud solicitud) {
        String correlativo, sigla, consultamd5, codSeg, anio;
        double div;
        int idtipoDocOficial = solicitud.getTipoSolicitud().getId();
        int edicionEstudio = solicitud.getEdicionestudio().getId();
        int idalumno = solicitud.getAlumno().getId();

        String sql = "SELECT COUNT(IDSOLICITUD) FROM SOLICITUD "
                + "WHERE DATEPART(YEAR,FECHASOLICITUD)= DATEPART(YEAR,GETDATE()) AND CODIGOSEG IS NOT NULL";

        correlativo = this.getJdbcTemplate().queryForInt(sql) + 1 + "";
        switch (correlativo.length()) {
            case 1:
                correlativo = "000" + correlativo;
                break;
            case 2:
                correlativo = "00" + correlativo;
                break;
            case 3:
                correlativo = "0" + correlativo;
            default:
                break;
        }
        /*GENERANDO EL CODIGO DE SEGURIDAD*/
        if (idalumno > solicitud.getId()) {
            div = (Math.pow(((idalumno / solicitud.getId()) * idtipoDocOficial), 3)) / edicionEstudio;
        } else {
            div = (Math.pow(((solicitud.getId() / idalumno) * Math.pow(idtipoDocOficial, 2)), 2)) / edicionEstudio;
        }

        sigla = getSiglaTipoDocOficial(solicitud.getTipoSolicitud().getId());

        consultamd5 = "SELECT [dbo].[MD5]([dbo].[MD5]('" + div + "' + '" + sigla + "') + :correlativo)";
        MapSqlParameterSource paramsMd5 = new MapSqlParameterSource();
        paramsMd5.addValue("correlativo", correlativo);

        codSeg = this.getNamedParameterJdbcTemplate().queryForObject(consultamd5, paramsMd5,
                String.class);

        /*CODIGO DE SEGURIDAD*/
        solicitud.setCodSeguridad(codSeg.substring(0, 1) + codSeg.substring(codSeg.length() / 2,
                (codSeg.length() / 2) + 1) + codSeg.substring(codSeg.length() - 3, codSeg.length() - 2));

        /*NUMERO SOLICITUD*/
        Calendar now = Calendar.getInstance();
        anio = now.get(Calendar.YEAR) + "";
        solicitud.setNroSolicitud(sigla + "-" + correlativo + "-" + anio.substring(anio.length() - 2));

        return solicitud;
    }

    @Override
    @Transactional
    public int saveDocumentoOficial(Solicitud solicitud) {
        String sql = "INSERT INTO SOLICITUD (IDTIPODOCOFICIAL,IDESTADOSOLICITUD,IDALUMNO,"
                + "IDEDICIONESTUDIO,FECHASOLICITUD,OBSSOLICITUD,NROSOLICITUD,CODIGOSEG,"
                + "IDIOMA,IDCAMPUSENTREGA,VOUCHER,ARCHIVO,IDPERSONA) "
                + "VALUES(:iddocoficial,:estado,:alumno,:idedicionEstudio,GETDATE(),:obssolicitud,"
                + ":nroSolicitud,:codSeg,:idioma,:campusEntrega,:voucher,0,:idpersona) ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nroSolicitud", solicitud.getNroSolicitud());
        params.addValue("estado", EstadoDocumentoOficial.SOLICITADO.getId());
        params.addValue("alumno", solicitud.getAlumno().getId());
        params.addValue("iddocoficial", solicitud.getTipoSolicitud().getId());
        params.addValue("idedicionEstudio", solicitud.getEdicionestudio().getId());
        params.addValue("obssolicitud", solicitud.getObsAlumno());
        params.addValue("codSeg", solicitud.getCodSeguridad());
        params.addValue("idioma", solicitud.getIdioma().getId());
        params.addValue("campusEntrega", solicitud.getCampusEntrega().getId());
        params.addValue("voucher", solicitud.isVoucher());
        params.addValue("idpersona", solicitud.getAlumno().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.getNamedParameterJdbcTemplate().update(sql, params, keyHolder);

        solicitud.setId(keyHolder.getKey().intValue());
        solicitud = generarCodigos(solicitud);

        sql = "UPDATE SOLICITUD SET NROSOLICITUD = :nroSolicitud, "
                + "CODIGOSEG = :codigoSeg WHERE IDSOLICITUD = :id";
        params = new MapSqlParameterSource();
        params.addValue("nroSolicitud", solicitud.getNroSolicitud());
        params.addValue("codigoSeg", solicitud.getCodSeguridad());
        params.addValue("id", solicitud.getId());

        this.getNamedParameterJdbcTemplate().update(sql, params);
        return solicitud.getId();
    }

    @Override
    public List<TipoSolicitud> getTipoDocOficialesList(List<Rol> rolList, int idCampus) {
        //verificar antes que estado sea 'Alumno'
        String sql = "SELECT IDTIPODOCOFICIAL, NOMBRE FROM TIPODOCOFICIAL "
                + "WHERE (IDCAMPUS=:campus OR IDCAMPUS IS NULL) ";

        if (rolList.contains(Rol.ALUMNO_PREGRADO)) {
            sql += "AND IDTIPODOCOFICIAL NOT IN (23,24,25,5,7,10,14,15) ";
        }
        if (rolList.contains(Rol.ALUMNO_POSGRADO)) {
            sql += "AND IDTIPODOCOFICIAL IN (16,22,2,20,3) ";
        }
        sql += "ORDER BY NOMBRE ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("campus", idCampus);

        List<TipoSolicitud> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getTipoDocOficialMapper());
        return list;
    }

    public int getCountHistorialEstado(int idAlumno) {
        String sql = "SELECT COUNT(*) FROM HISTORIALESTADOSOLICITUD WHERE IDSOLICITUD IN "
                + "(SELECT S.IDSOLICITUD FROM SOLICITUD S WHERE S.IDALUMNO = :idAlumno "
                + ")AND DATEDIFF(DAY,FECHANEW,GETDATE()) <= :dias";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("dias", AppConstants.RESUMEN_DIAS);

        return this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
    }
}
