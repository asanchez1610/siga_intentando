package com.udep.siga.dao.impl;

import com.udep.siga.bean.CategoriaConsulta;
import com.udep.siga.bean.Consulta;
import com.udep.siga.bean.enumeration.EstadoConsulta;
import com.udep.siga.dao.ConsultaDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("consultaDAO")
public class IConsultaDAO extends CustomizeJdbcDaoSupport implements ConsultaDAO {

    @Override
    public List<Consulta> getPendientes(int idalumno) {
        String sql = "SELECT C.IDCONSULTASUGERENCIA,C.FECHA,C.IDESTADOCONSULTAS,CAT.IDCATEGORIACONSULTAS, "
                + "CAT.NOMBRE 'CATEGORIA', C.POSITIVO,TEXTOCONSULTASUGERENCIA, "
                + "FECHARESPUESTA,TEXTORESPUESTA FROM CONSULTASSUGERENCIAS C, "
                + "CATEGORIACONSULTAS CAT WHERE C.IDCATEGORIACONSULTAS = CAT.IDCATEGORIACONSULTAS "
                + "AND C.IDPERSONA = :idpersona AND C.IDESTADOCONSULTAS <> :idEstado ORDER BY C.FECHA";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpersona", idalumno);
        params.addValue("idEstado", EstadoConsulta.RESPONDIDO.getId());

        List<Consulta> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getConsultaMapper());
        return list;
    }
    
    @Override
    public List<Consulta> getFinalizados (int idalumno) {
        String sql = "SELECT C.IDCONSULTASUGERENCIA,C.FECHA,C.IDESTADOCONSULTAS,CAT.IDCATEGORIACONSULTAS, "
                + "CAT.NOMBRE 'CATEGORIA', C.POSITIVO,TEXTOCONSULTASUGERENCIA, "
                + "FECHARESPUESTA,TEXTORESPUESTA FROM CONSULTASSUGERENCIAS C, "
                + "CATEGORIACONSULTAS CAT WHERE C.IDCATEGORIACONSULTAS = CAT.IDCATEGORIACONSULTAS "
                + "AND C.IDPERSONA = :idpersona AND C.IDESTADOCONSULTAS = :idEstado ORDER BY C.FECHA";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpersona", idalumno);
        params.addValue("idEstado", EstadoConsulta.RESPONDIDO.getId());

        List<Consulta> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getConsultaMapper());
        return list;
    }
    
    @Override
    public List<CategoriaConsulta> getCategorias () {
        String sql = "SELECT IDCATEGORIACONSULTAS,NOMBRE FROM CATEGORIACONSULTAS "
                + "WHERE ACTIVO = 1";
        List<CategoriaConsulta> list = this.getJdbcTemplate().query(sql,
                UtilRowMapper.getCategoriaConsultaMapper());
        return list;
    }
    
    @Override
    public CategoriaConsulta getCategoriaById (int idCategoria) {
        String sql = "SELECT IDCATEGORIACONSULTAS,NOMBRE FROM CATEGORIACONSULTAS "
                + "WHERE IDCATEGORIACONSULTAS = ?";
        List<CategoriaConsulta> list = this.getJdbcTemplate().query(sql,
                new Object[]{idCategoria},UtilRowMapper.getCategoriaConsultaMapper());
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public void saveConsulta(Consulta consulta, int idAlumno) {
        String sql = "INSERT INTO CONSULTASSUGERENCIAS (FECHA,TEXTOCONSULTASUGERENCIA,"
                + "IDPERSONA,IDCATEGORIACONSULTAS,IDESTADOCONSULTAS) "
                + "VALUES (GETDATE(),:texto, :idAlumno, :idCategoria,:estado) ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("texto",consulta.getConsulta());
        params.addValue("idAlumno", idAlumno);
        params.addValue("estado", EstadoConsulta.REALIZADO.getId());
        params.addValue("idCategoria", consulta.getCategoria().getId());

        this.getNamedParameterJdbcTemplate().update(sql, params);
    }
    
    public void EditConsulta(Consulta consulta, int idAlumno) {
        String sql = "UPDATE CONSULTASSUGERENCIAS "
                + "SET TEXTOCONSULTASUGERENCIA = :texto, "
                + "IDCATEGORIACONSULTAS = :idCategoria,FECHA = GETDATE() "
                + "WHERE  IDCONSULTASUGERENCIA = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("texto",consulta.getConsulta());
        params.addValue("idCategoria", consulta.getCategoria().getId());
        params.addValue("id", consulta.getId());

        this.getNamedParameterJdbcTemplate().update(sql, params);
    }
    
}
