package com.udep.siga.dao.impl;

import com.udep.siga.bean.Articulo;
import com.udep.siga.bean.HorarioAsesoria;
import com.udep.siga.bean.Interaccion;
import com.udep.siga.bean.InvestigacionGenerica;
import com.udep.siga.bean.Libro;
import com.udep.siga.bean.Profesor;
import com.udep.siga.bean.Tesis;
import com.udep.siga.bean.TrabajoInvestigacion;
import com.udep.siga.bean.enumeration.TipoTesis;
import com.udep.siga.dao.ProfesorDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("profesorDAO")
public class IProfesorDAO extends CustomizeJdbcDaoSupport implements ProfesorDAO {
    
    @Override
    public Map<String, Object> getInfoAcademico(int idProfesor) {
        String sql = "SELECT O.NOMBRE 'OFICINA', E.NOMBRE 'EDIFICIO', P.ANEXO, DA.NOMBRE 'DEPARTAMENTO', "
                + "CA.NOMBRE 'CENTROACADEMICO' FROM PROFESOR P LEFT OUTER JOIN OFICINA O ON "
                + "P.IDOFICINA = O.IDOFICINA LEFT OUTER JOIN EDIFICIO E ON O.IDEDIFICIO = E.IDEDIFICIO "
                + "LEFT OUTER JOIN PROFESORDEPARTAMENTO PD ON P.IDPROFESOR = PD.IDPROFESOR LEFT OUTER JOIN "
                + "DEPAACADEMICO DA ON PD.IDDEPAACADEMICO = DA.IDDEPAACADEMICO LEFT OUTER JOIN CENTROACADEMICO CA "
                + "ON P.IDCENTROACADEMICO = CA.IDCENTROACADEMICO WHERE P.IDPROFESOR = ?";
        
        List<Map<String, Object>> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getHashMapMapper());
        if (list.isEmpty()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("oficina", "");
            item.put("edificio", "");
            item.put("anexo", "");
            item.put("departamento", "");
            item.put("centroacademico", "");
            return item;
        } else {
            return list.get(0);
        }
    }
    
    @Override
    public List<HorarioAsesoria> getHorarioAsesoriaList(int idProfesor) {
        String sql = "SELECT HA.IDDIA, HA.IDBLOQUEHORARIO, B.DESCRIPCION, B.ORDEN, "
                + "HA.TITULO, O.NOMBRE 'OFICINA', E.NOMBRE 'EDIFICIO' "
                + "FROM HORARIOASESORIA HA LEFT OUTER JOIN OFICINA O ON HA.IDOFICINA = O.IDOFICINA "
                + "LEFT OUTER JOIN EDIFICIO E ON O.IDEDIFICIO = E.IDEDIFICIO, BLOQUEHORARIO B "
                + "WHERE B.IDBLOQUEHORARIO = HA.IDBLOQUEHORARIO AND HA.VISIBLE = 1 AND "
                + "EXISTS(SELECT P.IDPERIODOACADEMICO FROM PERIODOACADEMICO P "
                + "WHERE P.VIGENTE = 1 AND P.IDPERIODOACADEMICO = HA.IDPERIODOACADEMICO) "
                + "AND HA.IDPERSONA = ? ";
        List<HorarioAsesoria> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getHorarioAsesoriaMapper());
        return list;
    }
    
    @Override
    public List<Interaccion> getAsesoriaInteraccionList(int idAlumno, int idAsesor) {
        String sql = "SELECT I.FECHA, I.DURACION, T.NOMBRE 'TEMA' FROM INTERACCION I, ASESORIA A, "
                + "PERIODOACADEMICO PA, TEMAINTERACCION T WHERE I.IDASESORIA = A.IDASESORIA AND "
                + "A.IDPERIODOACADEMICO = PA.IDPERIODOACADEMICO AND PA.VIGENTE = 1 AND A.VIGENTE = 1 AND "
                + "T.IDTEMAINTERACCION = I.IDTEMAINTERACCION AND A.IDPERSONAASESOR = ? AND A.IDALUMNO = ?";
        List<Interaccion> list = this.getJdbcTemplate().query(sql,
                new Object[]{idAsesor, idAlumno}, UtilRowMapper.getInteraccionMapper());
        return list;
    }
    
    @Override
    public List<Date> getAsesoriaFuturaList(int idAlumno, int idAsesor) {
        String sql = "SELECT E.FECHAPROGRAMADA FROM ENTREVISTAFUTURAASIGNADOS E, ASESORIA A, PERIODOACADEMICO PA "
                + "WHERE E.IDASESORIA = A.IDASESORIA AND A.IDPERIODOACADEMICO = PA.IDPERIODOACADEMICO AND "
                + "PA.VIGENTE = 1 AND A.VIGENTE = 1 AND  E.IDESTADOENTREVISTAFUTURA = 1 AND A.IDPERSONAASESOR = ? "
                + "AND A.IDALUMNO = ?";
        List<Date> list = this.getJdbcTemplate().query(
                sql, new Object[]{idAsesor, idAlumno}, UtilRowMapper.getDateMapper());
        return list;
    }
    
    @Override
    public List<Profesor> findByNombreApellidos(String nombre, String apePaterno, String apeMaterno) {
        String sql = "SELECT P.IDPERSONA,P.IDTIPODOCIDENTIDAD,P.IDSEXO,P.NOMBRES,P.APELLIDOPATERNO,"
                + "P.APELLIDOMATERNO,P.DNI,P.FOTO,P.ASESOR FROM PROFESOR PR, PERSONA P "
                + "WHERE PR.IDPROFESOR = P.IDPERSONA AND PR.ACTIVO = 1 ";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        
        if (!nombre.isEmpty()) {
            params.addValue("nombre", "%" + nombre + "%");
            sql += "AND P.NOMBRES LIKE :nombre ";
        }
        if (!apePaterno.isEmpty()) {
            params.addValue("apePaterno", "%" + apePaterno + "%");
            sql += "AND P.APELLIDOPATERNO LIKE :apePaterno ";
        }
        
        if (!apeMaterno.isEmpty()) {
            params.addValue("apeMaterno", "%" + apeMaterno + "%");
            sql += "AND P.APELLIDOMATERNO LIKE :apeMaterno ";
        }
        
        List<Profesor> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getProfesorMapper());
        return list;
    }
    
    @Override
    public List<TrabajoInvestigacion> getTrabajosInvestigacionByProfesor(int idProfesor) {
        String sql = "SELECT IDTRABAJOINVESTIGACION, TITULO FROM TRABAJOINVESTIGACION "
                + "WHERE ACTIVA=1 AND IDPROFESOR = ? AND IDTRABAJOINVESTIGACION NOT IN "
                + "(SELECT ISNULL(IDTRABAJOINVESTIGACION,0) FROM INVESTIGACIONGENERICA) ";
        
        List<TrabajoInvestigacion> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, new RowMapper() {
            public TrabajoInvestigacion mapRow(ResultSet rs, int rowNum) throws SQLException {
                TrabajoInvestigacion item = new TrabajoInvestigacion();
                item.setId(rs.getInt("IDTRABAJOINVESTIGACION"));
                item.setTitulo(rs.getString("TITULO"));
                
                return item;
            }
        });
        return list;
    }
    
    @Override
    public TrabajoInvestigacion getDetalleTrabajosInvestigacion(int idTrabajo) {
        String sql = "SELECT T.IDTRABAJOINVESTIGACION,TITULO,T.IDCAMPOINVESTIGACION, L.NOMBRE, "
                + "T.IDPROFESOR,T.DESCRIPCION,T.FECHAINICIO, T.FECHAFIN,T.RESULTADO,T.AUTORES "
                + "FROM TRABAJOINVESTIGACION T, LINEAINVESTIGACION L "
                + "WHERE T.IDCAMPOINVESTIGACION = L.IDLINEAINVESTIGACION AND T.ACTIVA = 1 "
                + "AND T.IDTRABAJOINVESTIGACION = ? AND T.IDTRABAJOINVESTIGACION NOT IN "
                + "(SELECT ISNULL(IDTRABAJOINVESTIGACION,0) FROM INVESTIGACIONGENERICA) ";
        
        List<TrabajoInvestigacion> list = this.getJdbcTemplate().query(sql,
                new Object[]{idTrabajo}, UtilRowMapper.getTrabajoInvestigacionMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
    @Override
    public List<Articulo> getArtByProfesor(int idProfesor) {
        String sql = "SELECT A.IDARTICULO, I.TITULO FROM ARTICULO A, INVESTIGACIONGENERICA I "
                + "WHERE A.IDINVESTIGACIONGENERICA= I.IDINVESTIGACIONGENERICA AND IDPROFESOR = ?";
        
        List<Articulo> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, new RowMapper() {
            public Articulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                Articulo item = new Articulo();
                item.setId(rs.getInt("IDARTICULO"));
                InvestigacionGenerica ig = new InvestigacionGenerica();                
                ig.setTitulo(rs.getString("TITULO"));
                item.setInvestigacionGenerica(ig);
                
                return item;
            }
        });
        return list;
    }
    
    @Override
    public List<Libro> getLibrosByProfesor(int idProfesor) {
        String sql = "SELECT L.IDLIBRO, I.TITULO, L.LIBROCOLECTIVO FROM LIBRO L, INVESTIGACIONGENERICA I "
                + "WHERE L.IDINVESTIGACIONGENERICA = I.IDINVESTIGACIONGENERICA AND  I.IDPROFESOR = ? ";
        
        List<Libro> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, new RowMapper() {
            public Libro mapRow(ResultSet rs, int rowNum) throws SQLException {
                Libro item = new Libro();
                item.setId(rs.getInt("IDLIBRO"));
                InvestigacionGenerica ig = new InvestigacionGenerica();                
                ig.setTitulo(rs.getString("TITULO"));
                item.setInvestigacionGenerica(ig);
                item.setColectivo(rs.getBoolean("LIBROCOLECTIVO"));
                
                return item;
            }
        });
        return list;
    }
    
    @Override
    public List<Tesis> getTesisByProfesor(int idProfesor) {
        String sql = "SELECT T.IDTESIS,I.TITULO,T.TIPO FROM TESIS T, INVESTIGACIONGENERICA I "
                + "WHERE T.IDINVESTIGACIONGENERICA = I.IDINVESTIGACIONGENERICA "
                + "AND I.IDPROFESOR =  ? ORDER BY T.TIPO ";
        
        List<Tesis> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, new RowMapper() {
            public Tesis mapRow(ResultSet rs, int rowNum) throws SQLException {
               Tesis item = new Tesis();
                item.setId(rs.getInt("IDTESIS"));
                InvestigacionGenerica ig = new InvestigacionGenerica();                
                ig.setTitulo(rs.getString("TITULO"));
                item.setInvestigacionGenerica(ig);
                item.setTipoTesis(TipoTesis.parse(rs.getInt("TIPO")));
                
                return item;
            }
        });
        return list;
    }
}
