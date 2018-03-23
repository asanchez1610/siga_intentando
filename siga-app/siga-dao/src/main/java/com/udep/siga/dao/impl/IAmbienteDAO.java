package com.udep.siga.dao.impl;

import java.math.BigDecimal;
import java.sql.Types;
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
	public List<Ambiente> getAmbientes(int idUnidad, int idDia, int idPeriodoAcademico, int idCampus) {
		// TODO Auto-generated method stub
		Date fechaHoy=new Date();
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
				.withProcedureName(BDConstants.SP_GET_AMBIENTES_DISPONIBLES)
				.returningResultSet("resulset", UtilRowMapper.getAmbienteMapper());

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("IDUNIDAD", idUnidad, Types.INTEGER);
		params.addValue("RANGOHORARIO", fechaHoy.getHours(), Types.INTEGER);
		params.addValue("IDDIA", idDia, Types.INTEGER);
		params.addValue("IDPERIODOACADEMICO", idPeriodoAcademico, Types.INTEGER);
		params.addValue("IDCAMPUS", idCampus, Types.INTEGER);
		System.out.println("valores :"+fechaHoy.getHours()+","+ + idUnidad + " ," + idDia + "," + idPeriodoAcademico + "," + idCampus);
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
		String sql = "SELECT DATEPART(HOUR, FECHAHORAINICIO) as HORA_INICIO,DATEPART(HOUR, FECHAHORAFIN) as HORA_FIN,S2_FECHASEVENTO.* FROM S2_FECHASEVENTO WHERE IDAMBIENTE = :idAmbiente  AND CAST(FECHAHORAINICIO as DATE) = :fecha";
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
