package com.udep.siga.dao.impl;

import com.udep.siga.bean.Material;
import com.udep.siga.dao.MaterialDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.AppConstants;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("materialDao")
public class IMaterialDAO extends CustomizeJdbcDaoSupport implements MaterialDAO {

    /*
     * Obtiene lista de Material de una asignatura - sección por tipo.
     */
    public List<Material> getMaterialByTipo(int idAsignatura, int idSeccion, int tipo) {

        String sql = "SELECT M.IDMATERIALASIGNATURA, M.NOMBRE, M.DESCRIPCION, M.FECHA, M.RUTAARCHIVO, NOMBREARCHIVO,"
                + "M.IDPROFESOR,M.PESO,M.TOCENTROCOPIADO FROM MATERIALASIGNATURA M WHERE M.IDASIGNATURADICTADA = :idAsignatura "
                + "AND M.IDTIPOMATERIAL = :tipo AND (M.IDSECCION = :idSeccion OR M.PUBLICO = 1  "
                + "AND (M.IDPROFESOR IN (SELECT PD.IDPROFESOR FROM PROFESORDICTA PD, "
                + "PROFESOR P WHERE PD.IDPROFESOR = P.IDPROFESOR AND PD.IDASIGNATURADICTADA= :idAsignatura "
                + "AND PD.IDSECCION = :idSeccion))) ORDER BY M.FECHA DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tipo", tipo);
        params.addValue("idSeccion", idSeccion);
        params.addValue("idAsignatura", idAsignatura);

        List<Material> materiales = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getMaterialMapper());

        return materiales;
    }

    /*
     * Obtiene Material  
     */
    public Material getMaterialById(int id) {

        String sql = "SELECT IDMATERIALASIGNATURA, NOMBRE, DESCRIPCION, FECHA, RUTAARCHIVO, NOMBREARCHIVO, "
                + "IDPROFESOR, PESO, TOCENTROCOPIADO FROM MATERIALASIGNATURA WHERE IDMATERIALASIGNATURA = :id ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        Material material = (Material) this.getNamedParameterJdbcTemplate()
                .queryForObject(sql, params, UtilRowMapper.getMaterialMapper());

        return material;
    }
    /*
     * Obtiene total de Material nuevo en los ultimos 3 dias para mostrar a los alumnos
     */

    public int getTotalMaterialNuevo(int idAsignatura, int idSeccion) {

        String sql = "SELECT COUNT(*) FROM MATERIALASIGNATURA M WHERE M.IDASIGNATURADICTADA = :idAsignatura "
                + "AND (M.IDSECCION = :idSeccion OR M.PUBLICO = 1 AND (M.IDPROFESOR IN "
                + "(SELECT PD.IDPROFESOR FROM PROFESORDICTA PD, PROFESOR P "
                + "WHERE PD.IDPROFESOR = P.IDPROFESOR AND PD.IDASIGNATURADICTADA= :idAsignatura "
                + "AND PD.IDSECCION = :idSeccion))) "
                + "AND DATEDIFF(DAY,M.FECHA,GETDATE()) <= :dias ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idSeccion", idSeccion);
        params.addValue("idAsignatura", idAsignatura);
        params.addValue("dias", AppConstants.RESUMEN_DIAS);

        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
        return count;
    }
}
