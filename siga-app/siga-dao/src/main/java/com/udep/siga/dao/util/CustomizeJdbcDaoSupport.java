package com.udep.siga.dao.util;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 *
 * @author Wilfredo Atoche
 */
public class CustomizeJdbcDaoSupport extends NamedParameterJdbcDaoSupport{
    
    @Autowired    
    public void initConfiguracion(DataSource dataSource) {        
        this.setDataSource(dataSource);   
    }
}
