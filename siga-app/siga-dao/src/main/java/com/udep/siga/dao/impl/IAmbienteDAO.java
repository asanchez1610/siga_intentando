package com.udep.siga.dao.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.udep.siga.bean.Ambiente;
import com.udep.siga.bean.FechaEvento;
import com.udep.siga.bean.Unidad;
import com.udep.siga.dao.AmbienteDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.BDConstants;

@Repository("ambienteDAO")
public class IAmbienteDAO extends CustomizeJdbcDaoSupport implements AmbienteDAO {

	@SuppressWarnings("unchecked")
	public List<Ambiente> getAmbientes(String fechaHoy, String fechaMaxima, String fechaMinima,int idUnidad,  int idPeriodoAcademico, int idCampus) {
		// TODO Auto-generated method stub
		
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
				.withProcedureName(BDConstants.SP_GET_AMBIENTES_DISPONIBLES)
				.returningResultSet("resulset", UtilRowMapper.getAmbienteMapper());

		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("IDUNIDAD", idUnidad, Types.INTEGER);
		params.addValue("RANGOHORARIO",fechaHoy , Types.TIMESTAMP);
		params.addValue("IDPERIODOACADEMICO", idPeriodoAcademico, Types.INTEGER);
		params.addValue("IDCAMPUS", idCampus, Types.INTEGER);
		params.addValue("LIMITEMAXIMO", fechaMaxima, Types.TIMESTAMP);
		params.addValue("LIMITEMINIMO",fechaMinima , Types.TIMESTAMP);
		System.out.println("valores :"+ fechaHoy+","+fechaMaxima+","+fechaMinima+","+ + idUnidad + "," + idPeriodoAcademico + "," + idCampus);
		List<Ambiente> resulset = (List<Ambiente>) simpleJdbcCall.execute(params).get("resulset");

		if (resulset.isEmpty())
			return null;
		return resulset;
	}

	@SuppressWarnings("unchecked")
	public List<Unidad> getUnidadSede(int idCampus) {
		String sql =  "SELECT UNI.IDUNIDAD,	UNI.NOMBRE FROM  S2_UNIDADES UNI"
					+" WHERE UNI.IDSEDEINFRAESTRUCTURA=:idCampus";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idCampus", idCampus);

		List<Unidad> list = this.getNamedParameterJdbcTemplate().query(sql, params, UtilRowMapper.getUnidadAllMapper());
		if (list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FechaEvento> eventosPorAmbiente(int idAmbiente, String fecha) {
		
		String sql = "SELECT\r\n" + 
						"	DATEPART(\r\n" + 
						"		HOUR,\r\n" + 
						"		fe.FECHAHORAINICIO\r\n" + 
						"	) as HORA_INICIO,\r\n" + 
						"	DATEPART(\r\n" + 
						"		HOUR,\r\n" + 
						"		fe.FECHAHORAFIN\r\n" + 
						"	) as HORA_FIN,\r\n" + 
						"	fe.*\r\n" + 
					"FROM\r\n" + 
					"	S2_FECHASEVENTO fe\r\n" + 
					"INNER JOIN S2_ESTADOSFECHAEVENTO efe on	efe.IDFECHAEVENTO = fe.IDFECHAEVENTO\r\n" + 
					"WHERE\r\n" + 
					"	fe.IDAMBIENTE = :idAmbiente\r\n" + 
					"	AND CAST(\r\n" + 
					"		fe.FECHAHORAINICIO as DATE\r\n" + 
					"	)= :fecha\r\n" + 
					"	AND efe.IDESTADO = 1\r\n" + 
					"	AND efe.IDESTADOFECHAEVENTO = (SELECT MAX(S2_ESTADOSFECHAEVENTO.IDESTADOFECHAEVENTO) FROM S2_ESTADOSFECHAEVENTO WHERE IDFECHAEVENTO = fe.IDFECHAEVENTO)";
		
		System.out.println(sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idAmbiente", idAmbiente);
		params.addValue("fecha", fecha);
		List<FechaEvento> list = this.getNamedParameterJdbcTemplate().query(sql, params, UtilRowMapper.getEventosPorAmbienteAllMapper());
		if (list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}

}
