package com.udep.siga.dao.impl;

import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Departamento;
import com.udep.siga.bean.Distrito;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.Provincia;
import com.udep.siga.bean.Usuario;
import com.udep.siga.dao.UsuarioDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.AppConstants;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("usuarioDAO")
public class IUsuarioDAO extends CustomizeJdbcDaoSupport implements UsuarioDAO {

    @Override
    public Usuario getUsuarioByLogin(String login) {
        String sql = "SELECT IDPERSONA, LOGIN, ACTIVO FROM USUARIO WHERE login = :login";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        List<Usuario> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getUsuarioMapper());

        if (list.size() > 0) {
            Usuario usuario = list.get(0);

            sql = "SELECT (SELECT COUNT(DISTINCT AE.IDEDICIONESTUDIO) FROM ALUMNOESTUDIO AE JOIN EDICIONESTUDIO ED ON AE.IDEDICIONESTUDIO = ED.IDEDICIONESTUDIO "
                    + "JOIN ALUMNOESTUDIOPERIODOACADEMICO AEPA ON AEPA.IDALUMNO = AE.IDALUMNO AND AEPA.IDEDICIONESTUDIO = AE.IDEDICIONESTUDIO, "
                    + "ESTUDIO E WHERE AE.IDALUMNO = :idAlumno AND AE.IDESTADOALUMNO = 1 AND E.IDESTUDIO = ED.IDESTUDIO AND "
                    + "E.IDTIPOESTUDIO = 1) 'PREGRADO', (SELECT COUNT(DISTINCT AE.IDEDICIONESTUDIO) FROM ALUMNOESTUDIO AE JOIN EDICIONESTUDIO ED ON "
                    + "AE.IDEDICIONESTUDIO = ED.IDEDICIONESTUDIO JOIN ALUMNOESTUDIOPERIODOACADEMICO AEPA ON AEPA.IDALUMNO = AE.IDALUMNO "
                    + "AND AEPA.IDEDICIONESTUDIO = AE.IDEDICIONESTUDIO, ESTUDIO E WHERE AE.IDALUMNO = :idAlumno AND AE.IDESTADOALUMNO = 1 "
                    + "AND E.IDESTUDIO = ED.IDESTUDIO AND E.IDTIPOESTUDIO IN (2,4,8)) 'POSGRADO' ";
            params = new MapSqlParameterSource();
            params.addValue("idAlumno", usuario.getId());
            try {
                Map<String, Boolean> result = (Map<String, Boolean>) this.getNamedParameterJdbcTemplate()
                        .queryForObject(sql, params, new RowMapper() {
                            public Map<String, Boolean> mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Map<String, Boolean> item = new HashMap<String, Boolean>();
                                item.put("PREGRADO", false);
                                item.put("POSGRADO", false);
                                if (rs.getInt("PREGRADO") > 0) {
                                    item.put("PREGRADO", true);
                                }
                                if (rs.getInt("POSGRADO") > 0) {
                                    item.put("POSGRADO", true);
                                }
                                return item;
                            }
                        });

                usuario.setPregrado(result.get("PREGRADO"));
                usuario.setPosgrado(result.get("POSGRADO"));
            } catch (IncorrectResultSizeDataAccessException ex) {
                usuario.setPregrado(false);
                usuario.setPosgrado(false);
            }
            /* Verificamos si es Exalumno o Egresado */
            if (!(usuario.isPregrado() || usuario.isPosgrado())) {
                params = new MapSqlParameterSource();
                params.addValue("idAlumno", usuario.getId());
                params.addValue("dias", AppConstants.PLAZO_ACCESO_EG_EX_DIAS);
                //Pregrado
                sql = "SELECT COUNT(*) FROM ALUMNOESTUDIOPERIODOACADEMICO AEP, ALUMNOESTUDIO AE, PERIODOACADEMICO P, EDICIONESTUDIO ED, ESTUDIO E "
                        + "WHERE AEP.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO AND AE.IDALUMNO = AEP.IDALUMNO AND "
                        + "AE.IDEDICIONESTUDIO = AEP.IDEDICIONESTUDIO AND AE.IDEDICIONESTUDIO = ED.IDEDICIONESTUDIO AND ED.IDESTUDIO = E.IDESTUDIO "
                        + "AND AE.IDESTADOALUMNO IN (2,3) AND E.IDTIPOESTUDIO = 1  AND AEP.IDALUMNO = :idAlumno AND DATEDIFF(DAY, P.FECHAFIN, GETDATE()) <= :dias";
                Integer count = this.getNamedParameterJdbcTemplate()
                        .queryForObject(sql, params, Integer.class);
                usuario.setExEgPregrado(false);
                if (count > 0) {
                    usuario.setExEgPregrado(true);
                }
                //Posgrado
                sql = "SELECT COUNT(*) FROM ALUMNOESTUDIOPERIODOACADEMICO AEP, ALUMNOESTUDIO AE, PERIODOACADEMICO P, EDICIONESTUDIO ED, ESTUDIO E "
                        + "WHERE AEP.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO AND AE.IDALUMNO = AEP.IDALUMNO AND "
                        + "AE.IDEDICIONESTUDIO = AEP.IDEDICIONESTUDIO AND AE.IDEDICIONESTUDIO = ED.IDEDICIONESTUDIO AND ED.IDESTUDIO = E.IDESTUDIO "
                        + "AND AE.IDESTADOALUMNO IN (2,3) AND E.IDTIPOESTUDIO IN (2,8) AND AEP.IDALUMNO = :idAlumno AND DATEDIFF(DAY, P.FECHAFIN, GETDATE()) <= :dias";
                count = this.getNamedParameterJdbcTemplate()
                        .queryForObject(sql, params, Integer.class);
                usuario.setExEgPosgrado(false);
                if (count > 0) {
                    usuario.setExEgPosgrado(true);
                }
            }

            return usuario;
        } else {
            return null;
        }
    }

    @Override
    public String getPasswordGeneral() {
        String sql = "SELECT TOP 1 LLAVE FROM V3_OPENITUPSESAMUS";
        String pwdGeneral = (String) this.getJdbcTemplate().queryForObject(sql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("LLAVE");
            }
        });
        return pwdGeneral;
    }

    @Override
    public Persona getPersonaById(int id) {
        String sql = "SELECT P.IDPERSONA,P.IDTIPODOCIDENTIDAD,P.IDSEXO,P.NOMBRES,"
                + "P.APELLIDOPATERNO,P.APELLIDOMATERNO,P.DNI,P.FOTO,P.ASESOR "
                + "FROM PERSONA P WHERE P.IDPERSONA = ?";

        Persona persona = (Persona) this.getJdbcTemplate().queryForObject(sql,
                new Object[]{id}, UtilRowMapper.getPersonaMapper());

        return persona;
    }

    @Override
    public DatoPersonal getDatoPersonalById(int id) {
        String sql = "SELECT FECHANACIMIENTO, TELEFONOMOVIL, DIRECCIONCASA, "
                + "TELEFONOCASA,DIRECCIONPENSION,TELEFONOPENSION,TELEFONOEMERGENCIA,"
                + "PERSONACONTACTOEMERGENCIA,IDDISTRITOCASA,IDDISTRITOPENSION, "
                + "BLOG,PAGINAPERSONAL,TWITTER,GOOGLESCHOLAR "
                + "FROM DATOSPERSONALES WHERE IDPERSONA = ?";
        List<DatoPersonal> datoPersonalList = this.getJdbcTemplate().query(sql,
                new Object[]{id}, UtilRowMapper.getDatoPersonalMapper());
        if (datoPersonalList.isEmpty()) {
            return null;
        } else {
            return datoPersonalList.get(0);
        }
    }

    @Override
    public Distrito loadProvinciaDepartamentoByIdDistrito(int idDistrito) {
        String sql = "SELECT D.IDDISTRITO, D.NOMBRE 'DISTRITO', P.IDPROVINCIA, P.NOMBRE 'PROVINCIA', "
                + "DE.IDDEPARTAMENTO, DE.NOMBRE 'DEPARTAMENTO' "
                + "FROM DISTRITO D, PROVINCIA P, DEPARTAMENTO DE "
                + "WHERE D.IDPROVINCIA = P.IDPROVINCIA AND P.IDDEPARTAMENTO = DE.IDDEPARTAMENTO AND D.IDDISTRITO = ?";
        Distrito distrito = (Distrito) this.getJdbcTemplate().queryForObject(sql,
                new Object[]{idDistrito}, UtilRowMapper.getDistritoFullMapper());

        return distrito;
    }

    @Override
    public List<Distrito> getDistritoList(int idProvincia) {
        String sql = "SELECT D.IDDISTRITO, D.NOMBRE FROM DISTRITO D WHERE D.IDPROVINCIA = ?";
        List<Distrito> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProvincia}, UtilRowMapper.getDistritoMapper());
        return list;
    }

    @Override
    public List<Provincia> getProvinciaList(int idDepartamento) {
        String sql = "SELECT P.IDPROVINCIA, P.NOMBRE FROM PROVINCIA P WHERE P.IDDEPARTAMENTO = ?";
        List<Provincia> list = this.getJdbcTemplate().query(sql,
                new Object[]{idDepartamento}, UtilRowMapper.getProvinciaMapper());
        return list;
    }

    @Override
    public List<Departamento> getDepartamentoList() {
        String sql = "SELECT IDDEPARTAMENTO, NOMBRE FROM DEPARTAMENTO";
        List<Departamento> list = this.getJdbcTemplate().query(sql, UtilRowMapper.getDepartamentoMapper());
        return list;
    }

    @Override
    public ArrayList<String> updatePassword(String login, String pass, String newPass, String repeatNewPass) {
        ArrayList<String> errores = new ArrayList<String>(0);

        String sql = "SELECT COUNT(*) FROM USUARIO WHERE login = :login AND PASS = dbo.MD5(:pass) ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        params.addValue("pass", pass);

        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql,
                params, Integer.class);

        if (count == 0) {
            errores.add("La contraseña actual no es correcta");
        }

        MapSqlParameterSource paramsNew = new MapSqlParameterSource();
        paramsNew.addValue("login", login);
        paramsNew.addValue("pass", newPass);

        count = this.getNamedParameterJdbcTemplate().queryForObject(sql,
                paramsNew, Integer.class);

        if (count != 0) {
            errores.add("La contraseña nueva no puede ser la misma");
        }
        if (errores.isEmpty()) {
            sql = "UPDATE USUARIO SET PASS = dbo.MD5(:pass) WHERE login = :login ";
            this.getNamedParameterJdbcTemplate().update(sql, paramsNew);
        }

        return errores;
    }

    public Boolean isAsistente(int id, int idasignatura, int idseccion) {
        //To change body of generated methods, choose Tools | Templates.
        MapSqlParameterSource params = new MapSqlParameterSource();
        Integer count = 0;
        params.addValue("idseccion", idseccion);
        params.addValue("idasistente", id);
        params.addValue("idasig", idasignatura);
        //Pregrado
        String sql = "SELECT COUNT(*) FROM ASISTENTEPROFESOR "
                + "WHERE IDASIGNATURADICTADA= :idasig AND IDSECCION= :idseccion AND "
                + "IDPERSONAASISTENTE = :idasistente ";

        count = this.getNamedParameterJdbcTemplate()
                .queryForObject(sql, params, Integer.class);
        return count > 0;

    }

    public Integer getIdProfesor(int id, int idasignatura, int idseccion) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        Integer count = 0;
        params.addValue("idseccion", idseccion);
        params.addValue("idasistente", id);
        params.addValue("idasig", idasignatura);
        //Pregrado
        String sql = "SELECT IDPROFESOR FROM ASISTENTEPROFESOR "
                + "WHERE IDASIGNATURADICTADA= :idasig AND IDSECCION= :idseccion AND "
                + "IDPERSONAASISTENTE = :idasistente ";

        count = this.getNamedParameterJdbcTemplate()
                .queryForObject(sql, params, Integer.class);
        return count; //To change body of generated methods, choose Tools | Templates.
    }
}
