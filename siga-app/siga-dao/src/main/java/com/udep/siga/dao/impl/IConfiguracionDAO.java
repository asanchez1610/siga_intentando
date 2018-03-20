package com.udep.siga.dao.impl;

import com.udep.siga.bean.enumeration.Configuracion;
import com.udep.siga.dao.ConfiguracionDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("configuracionDAO")
public class IConfiguracionDAO extends CustomizeJdbcDaoSupport implements ConfiguracionDAO{
    
    @Override
    public String getConfiguracion(Configuracion configuracion) {
        String sql = "SELECT VALOR FROM CONFIGURACION WHERE ID = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", configuracion.getId());

        List<String> list = this.getNamedParameterJdbcTemplate().queryForList(sql, params,
                String.class);
        if(list.isEmpty()){
            return "";
        }
        return list.get(0);
    }
    
    @Override
    public String getConfiguracion1(Configuracion configuracion) {
        String sql = "SELECT VEREN FROM CONFIGURACION WHERE ID = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", configuracion.getId());

        List<String> list = this.getNamedParameterJdbcTemplate().queryForList(sql, params,
                String.class);
        if(list.isEmpty()){
            return "";}
        return list.get(0);
    }
}