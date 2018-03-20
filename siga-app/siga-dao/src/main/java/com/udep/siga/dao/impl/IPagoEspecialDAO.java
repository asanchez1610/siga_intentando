package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.FechaCuotaPagoEspecial;
import com.udep.siga.bean.PensionAlumno;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.TipoPagoEspecial;
import com.udep.siga.dao.PagoEspecialDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrador
 */
@Repository("pagoEspecialDAO")
public class IPagoEspecialDAO extends CustomizeJdbcDaoSupport implements PagoEspecialDAO{
    
    private JdbcTemplate jdbcTemplateAux;
    @Autowired
    @Qualifier("dataSourcePPiura")
    private DataSource dataSourcePPiura;
    @Autowired
    @Qualifier("dataSourcePLima")
    private DataSource dataSourcePLima;
    
    public TipoPagoEspecial getTipoPagosEspecialesByAlumno(List<PensionAlumno> apLista){
        TipoPagoEspecial tipoPagoEspecial = null;
        if(apLista != null &&  apLista.size() > 0) {
            PensionAlumno ap = apLista.get(0);
            tipoPagoEspecial = TipoPagoEspecial.ORDINARIO;
            boolean isEspecial = ap.isEspecial();
            boolean isPagoQuincena = ap.isPagoQuincena();
            
            if(isEspecial){
                if(isPagoQuincena){
                    tipoPagoEspecial = TipoPagoEspecial.ESPECIAL_QUINCENA;
                }else{
                    tipoPagoEspecial = TipoPagoEspecial.ESPECIAL_FIN_DE_MES;
                }
            }
        }
        return tipoPagoEspecial;	
    }
    
    public boolean isActivo(int idPeriodoAcademico, int idCampus){
        String sql = "SELECT COUNT(*) FROM PAGOSESPECIALES "
                + "WHERE IDPERIODOACADEMICO = :idPeriodo AND IDCAMPUS = :idCampus "
                + "AND GETDATE() BETWEEN FECHAINICIO AND FECHAFIN";
        
	MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodo", idPeriodoAcademico);
        params.addValue("idCampus", idCampus);
        
	int activo = this.getNamedParameterJdbcTemplate().queryForObject
                (sql,params,Integer.class);
        if (activo > 0) {
            return true;
        } else {
            return false;
        }	
    }
    
    public List<TipoPagoEspecial> getTipoPagoEspecialList(){
        String sql = "SELECT T.IDTIPOPAGOSESPECIALES "
                + "FROM  TIPOPAGOSESPECIALES T ORDER BY 1";

	List<TipoPagoEspecial> list = this.getJdbcTemplate().query(sql, UtilRowMapper.getTipoPagoEspecialMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }	
    }
    
    public TipoPagoEspecial getTipoPagoEspecial(int id){
        String sql = "SELECT IDTIPOPAGOSESPECIALES "
                + "FROM TIPOPAGOSESPECIALES WHERE IDTIPOPAGOSESPECIALES = :id";

	MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        
	List<TipoPagoEspecial> list = this.getNamedParameterJdbcTemplate().query
                (sql,params, UtilRowMapper.getTipoPagoEspecialMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }	
    }
    
    public List<FechaCuotaPagoEspecial> getFechaCuotaPagoEspecial(int idPeriodo,int idCampus,int idTipo){
        String sql = "SELECT FCPE.* FROM PAGOSESPECIALESTIPOPAGOSESPECIALES PT "
                + "INNER JOIN FECHASCUOTASPAGOSESPECIALES FCPE ON PT.IDPAGOSESPECIALES = FCPE.IDPAGOSESPECIALES "
                + "AND PT.IDTIPOPAGOSESPECIALES = FCPE.IDTIPOPAGOSESPECIALES,PAGOSESPECIALES P, TIPOPAGOSESPECIALES T "
                + "WHERE PT.IDPAGOSESPECIALES = P.IDPAGOSESPECIALES AND PT.IDTIPOPAGOSESPECIALES = T.IDTIPOPAGOSESPECIALES "
                + "AND P.IDPERIODOACADEMICO = :idPeriodo AND P.IDCAMPUS = :idCampus AND T.IDTIPOPAGOSESPECIALES = :idTipo ";

	MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodo", idPeriodo);
        params.addValue("idCampus", idCampus);
        params.addValue("idTipo", idTipo);
        
	List<FechaCuotaPagoEspecial> list = this.getNamedParameterJdbcTemplate().query
                (sql,params, UtilRowMapper.getFechaCuotaPagoEspecialMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }	
    }
    
    @Override
    @Transactional
    public void saveFechaCuotaPagoEspecial(int idAlumno, int idPagoEspecial, int idTipoPagoEspecial) {
        String sql = "INSERT INTO ALUMNOFECHASCUOTASPAGOSESPECIALES (IDALUMNO, IDPAGOSESPECIALES, "
                + "IDTIPOPAGOSESPECIALES, FECHASOLICITUD) VALUES(:idAlumno, :idPagoEspecial, "
                + ":idTipoPagoEspecial,GETDATE())";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idPagoEspecial", idPagoEspecial);
        params.addValue("idTipoPagoEspecial",idTipoPagoEspecial);

        this.getNamedParameterJdbcTemplate().update(sql, params);
    }
    
    @Override
    @Transactional
    public void saveEnPensiones(AlumnoEstudio alumnoEstudio,List<PensionAlumno> apLista,
    boolean isEspecial, boolean inQuincena) {
        if (alumnoEstudio.getCampus().getId() == Campus.Piura.getId()) {
            jdbcTemplateAux = new JdbcTemplate(dataSourcePPiura);
        } else if (alumnoEstudio.getCampus().getId() == Campus.Lima.getId()) {
            jdbcTemplateAux = new JdbcTemplate(dataSourcePLima);
        }
        boolean isProcesado = this.isProcesadoPagosEspecialesByAlumno(alumnoEstudio,apLista,false);
        Map<String, Object> item = this.getAlumnoPagosEspecialesPensiones
                (alumnoEstudio, apLista, false, isProcesado);
        
        String sql;
        String sqlAlumno = "UPDATE Alumno SET laluesp = ?, lalupqu = ? WHERE calu = ?";
        int idAlumnoPE = (Integer) item.get("idAlumno");
        int calu = (Integer) item.get("calu");
        if(isProcesado){
            sql = "UPDATE Alumno_PagosEspeciales SET FechaPeticion = GETDATE() "
                    + "WHERE IdAlumnoPE = ? ";  
            //Actualiza Tabla = "Alumno_PagosEspeciales", catalog = "pensiones_academicas"
            jdbcTemplateAux.update(sql, new Object[]{idAlumnoPE});
            //Actualiza  Tabla = "Alumno", catalog = "pensiones_academicas"       
            jdbcTemplateAux.update(sqlAlumno, new Object[]{isEspecial,inQuincena,calu});
        }else{
            sql = "INSERT INTO Alumno_PagosEspeciales(calu,FechaPeticion,procesado,FechaProceso) "
                    + "VALUES(?,GETDATE(),0,GETDATE()) ";
            //guardar en Tabla = "Alumno_PagosEspeciales", catalog = "pensiones_academicas"
            jdbcTemplateAux.update(sql, new Object[]{apLista.get(0).getCalu()});
            //Actualiza  Tabla = "Alumno", catalog = "pensiones_academicas"       
            jdbcTemplateAux.update(sqlAlumno, new Object[]{isEspecial,inQuincena,apLista.get(0).getCalu()});
        }

    }
    
    public Map<String, Object> getAlumnoPagosEspecialesPensiones(AlumnoEstudio alumnoEstudio,
            List<PensionAlumno> apLista,boolean procesado, boolean isProcesado){
        Map<String, Object> item = new HashMap<String, Object>();         
        
	if(alumnoEstudio != null && alumnoEstudio.getCampus() != null){   
            if (alumnoEstudio.getCampus().getId() == Campus.Piura.getId()) {
                jdbcTemplateAux = new JdbcTemplate(dataSourcePPiura);
            } else if (alumnoEstudio.getCampus().getId() == Campus.Lima.getId()) {
                jdbcTemplateAux = new JdbcTemplate(dataSourcePLima);
            }
            if(!procesado && isProcesado){
                
                String caluCadena = this.getCaluCadena(apLista);
                String sql = "SELECT TOP 1 apep.calu, apep.IdAlumnoPE FROM Alumno_PagosEspeciales AS apep "
                        + "WHERE apep.calu " + caluCadena + " AND apep.procesado = ? "
                        + "ORDER BY apep.IdAlumnoPE DESC ";
                
                item = (Map<String, Object>) jdbcTemplateAux.queryForObject
                        (sql,new Object[]{procesado},new RowMapper() {
                    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("calu", rs.getInt("calu"));
                        item.put("idAlumno", rs.getInt("IdAlumnoPE"));
                        return item;
                    }
                });
                
            }else {
                item.put("calu", 0);
                item.put("idAlumno", 0);
            }
        }
        return item;		
    }
    
    public boolean isProcesadoPagosEspecialesByAlumno(AlumnoEstudio alumnoEstudio,
            List<PensionAlumno> apLista,boolean procesado){
        boolean isProcesado = false;
	if(alumnoEstudio != null && alumnoEstudio.getCampus() != null){
            String caluCadena = this.getCaluCadena(apLista);
            isProcesado = this.isPagoEspecialProcesado(alumnoEstudio.getCampus()
                    .getId(),caluCadena,procesado);            
        }
        return isProcesado;
		
    }
    
    public String getCaluCadena(List<PensionAlumno> apLista){
        String caluCadena = " in (";
        for(int i = 0; apLista.size() > i; i++){				
            if( apLista.get(i) != null){
                    caluCadena += apLista.get(i).getCalu();
                    if( apLista.get(i) != null && apLista.size() > i+1 ){
                            caluCadena += ", ";
                    }
            }
        }
        caluCadena += ")";			
        if(caluCadena.equals(" in ()")){

                caluCadena = " = 0";
        }
        return caluCadena;		
    }
    
    public boolean isPagoEspecialProcesado(int idCampus, String caluCadena, boolean procesado){
        boolean isProcesado = false;
        if(idCampus!= 0){
            if (idCampus == Campus.Piura.getId()) {
                jdbcTemplateAux = new JdbcTemplate(dataSourcePPiura);
            } else if (idCampus == Campus.Lima.getId()) {
                jdbcTemplateAux = new JdbcTemplate(dataSourcePLima);
            }
            String sql = "SELECT COUNT(*) FROM Alumno_PagosEspeciales AS apep "
                    + "WHERE apep.calu " + caluCadena + " AND apep.procesado = ?";

            List<Integer> list = jdbcTemplateAux.queryForList(sql,new Object[]{procesado},Integer.class);
            if(!list.isEmpty()){
                if(list.get(0) != 0){
                    isProcesado = true;
                }                        
            }
        }
        return isProcesado;
    }
}
