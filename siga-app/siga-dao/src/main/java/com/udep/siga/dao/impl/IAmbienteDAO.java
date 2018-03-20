package com.udep.siga.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.udep.siga.bean.Ambiente;
import com.udep.siga.dao.AmbienteDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.BDConstants;


@Repository("ambienteDAO")
public class IAmbienteDAO extends CustomizeJdbcDaoSupport implements AmbienteDAO{

	
	public List<Ambiente> getAmbientes(int idBloqueoHoraio,int idDia,int idPeriodoAcademico,int idCampus) {
		// TODO Auto-generated method stub
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_AMBIENTES_DISPONIBLES)
                .returningResultSet("resulset", UtilRowMapper.getAvisoMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDBLOQUEHORARIO", idBloqueoHoraio)
                .addValue("IDDIA", idDia)
                .addValue("IDPERIODOACADEMICO", idPeriodoAcademico)
                .addValue("IDCAMPUS", idCampus);
        System.out.println("valores :"+ idBloqueoHoraio+" ,"+idDia+","+idPeriodoAcademico+","+ idCampus);
        List<Ambiente> resulset =  (List<Ambiente>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset.isEmpty())
            return null;
        return resulset;
	}

	
	
}
