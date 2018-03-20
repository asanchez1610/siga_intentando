package com.udep.siga.dao.impl;

import com.udep.siga.bean.CentroAcademico;
import com.udep.siga.bean.Departamento;
import com.udep.siga.bean.Distrito;
import com.udep.siga.bean.HorarioEvento;
import com.udep.siga.bean.Provincia;
import com.udep.siga.dao.UtilDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.BDConstants;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("utilDAO")
public class IUtilDAO extends CustomizeJdbcDaoSupport implements UtilDAO {

    @Override
    public List<Distrito> getDistritoList(int idProvincia) {
        String sql = "SELECT D.IDDISTRITO, D.NOMBRE FROM DISTRITO D WHERE D.IDPROVINCIA = ? ORDER BY D.NOMBRE";
        List<Distrito> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProvincia}, UtilRowMapper.getDistritoMapper());
        return list;
    }

    @Override
    public List<Provincia> getProvinciaList(int idDepartamento) {
        String sql = "SELECT P.IDPROVINCIA, P.NOMBRE FROM PROVINCIA P WHERE P.IDDEPARTAMENTO = ? ORDER BY P.NOMBRE";
        List<Provincia> list = this.getJdbcTemplate().query(sql,
                new Object[]{idDepartamento}, UtilRowMapper.getProvinciaMapper());
        return list;
    }

    @Override
    public List<Departamento> getDepartamentoList() {
        String sql = "SELECT IDDEPARTAMENTO, NOMBRE FROM DEPARTAMENTO ORDER BY NOMBRE";
        List<Departamento> list = this.getJdbcTemplate().query(sql, UtilRowMapper.getDepartamentoMapper());
        return list;
    }

    @Override
    public List<CentroAcademico> getCentroAcademicoList(int idCampus) {
        String sql = "SELECT C.IDCENTROACADEMICO, C.SIGLACENTRO, C.NOMBRE, C.ACTIVO "
                + "FROM CENTROACADEMICO C, CENTROACADEMICOCAMPUS CAC "
                + "WHERE C.IDCENTROACADEMICO = CAC.IDCENTROACADEMICO AND C.IDTIPOCENTROACADEMICO IN (1,2) "
                + "AND CAC.IDCAMPUS = ?";
        List<CentroAcademico> list = this.getJdbcTemplate().query(sql, new Object[]{idCampus},
                UtilRowMapper.getCentroAcademicoMapper());
        return list;
    }

    @Override
    public boolean getContieneEspaciosEnBlanco(String pass) {

        String espacio = " ";
        for (int i = 0; i < pass.length(); i++) {
            if (espacio.indexOf(pass.charAt(i)) != -1) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean getContieneNumeros(String pass) {

        String numeros = "0123456789";

        for (int i = 0; i < pass.length(); i++) {
            if (numeros.indexOf(pass.charAt(i)) != -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getContieneMinusculas(String pass) {

        String letrasMinus = "abcdefghijklmnñopqrstuvwxyz";

        for (int i = 0; i < pass.length(); i++) {
            if (letrasMinus.indexOf(pass.charAt(i)) != -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getContieneMayusculas(String pass) {

        String letrasMayus = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

        for (int i = 0; i < pass.length(); i++) {
            if (letrasMayus.indexOf(pass.charAt(i)) != -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNewFormatFecha(String fecha) {
        int dia = 0;
        int mes = 0;
        int anio = 0;

        if (fecha != null && !fecha.equals("")) {
            String[] fechaPublicacion = fecha.split("-");
            dia = Integer.parseInt(fechaPublicacion[0]);
            mes = Integer.parseInt(fechaPublicacion[1]);
            anio = Integer.parseInt(fechaPublicacion[2]);
        }

        return this.getNewFormatFecha(dia, mes, anio,false);
    }

    @Override
    public String getNewFormatFecha(int dia, int mes, int anio, boolean SimpleDateFormat) {

        SimpleDateFormat sdf;
        String pattern = "dd/MM/yyyy";
        String nuevo_pattern = "dd 'de' MMMM 'de' yyyy";
        String novo_pattern = pattern;
        if (dia == 0) {
            dia = 1;
            nuevo_pattern = "MMMM 'de' yyyy";
            if (SimpleDateFormat) {novo_pattern = "MM/yyyy";}
        }
        if (mes == 0) {
            mes = 1;
            nuevo_pattern = "yyyy";
            if (SimpleDateFormat) {novo_pattern = "yyyy";}
        }
        if (anio == 0) {
            return "";
        }
        sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(String.format("%02d/%02d/%04d", dia, mes, anio));
            if (SimpleDateFormat) {
                nuevo_pattern = novo_pattern;
            }
            sdf.applyPattern(nuevo_pattern);
            return sdf.format(date);
        } catch (ParseException ex) {
            return "";
        }
    }

    @Override
    public String getAnio(String fecha) {
        String[] datos = fecha.split("-");
        if (datos[2].equals("0")) {
            return "";
        }
        return datos[2];

    }
    
    public String getFechaActual() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DATE);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int anio = calendar.get(Calendar.YEAR);
        
        String dato = this.getNewFormatFecha(dia, mes, anio, true);
        dato += " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) 
                + ":" + calendar.get(Calendar.SECOND);
        return dato;

    }
    
    /* 
     * Obtener PeriodoPensiones
     */
    @Override
    public short getSemestreSigaToArgus(int idCampus, int idPeriodoAcademico) {

        String sql = "SELECT TOP 1 C.IDPERIODOPENSIONES FROM CODIGOSPENSIONES AS C WHERE C.IDCAMPUS = :idCampus "
                + "AND C.IDPERIODOACADEMICO = :idPeriodoAcademico";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idCampus", idCampus);
        params.addValue("idPeriodoAcademico", idPeriodoAcademico);

        List<Short> csemList = this.getNamedParameterJdbcTemplate().queryForList(sql, params, Short.class);
        if (csemList.isEmpty()) {
            return 0;
        } else {
            return csemList.get(0);
        }
    }

    public boolean isHorarioevento(int idalumno) {
        int valor=0;
        valor=this.getJdbcTemplate().queryForObject(BDConstants.FN_CHECK_HORARIOCALENDARIZADO, new Object[]{idalumno,2}, Integer.class);
        return valor>0;
        /*try{
        return simpleJdbcCall.executeObject(Integer.class) > 0;
        }catch(Exception ex){
        return false;
        }*/
    }

    public List<HorarioEvento> getHorarioeventoList(int idalumno,String fecha,int idperiodoacademico) {
         SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_LISTAHORARIOEVENTO)
                .returningResultSet("resulset", UtilRowMapper.getHorarioEventoMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDALUMNO", idalumno)
                .addValue("FECHA",fecha )
                 .addValue("IDPERIODO", idperiodoacademico);
        
        List<HorarioEvento> resulset = (List<HorarioEvento>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset == null || resulset.isEmpty())
            return null; 
        return resulset;
    }

    public List<String> getHorarioFechasList(String fecha) {
         SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_LISTAHORARIOFECHAS)
                .returningResultSet("resulset",new SingleColumnRowMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("FECHA", fecha);
        List<String> resulset = (List<String>) simpleJdbcCall.execute(params).get("resulset");
        if(resulset == null || resulset.isEmpty())
            return null; 
        return resulset;
               
    }
}
