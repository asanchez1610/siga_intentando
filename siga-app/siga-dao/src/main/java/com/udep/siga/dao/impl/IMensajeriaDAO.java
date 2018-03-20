package com.udep.siga.dao.impl;

import com.udep.siga.bean.Mensaje;
import com.udep.siga.bean.Email;
import com.udep.siga.dao.MensajeriaDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.AppConstants;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("mensajeriaDAO")
public class IMensajeriaDAO extends CustomizeJdbcDaoSupport implements MensajeriaDAO {

    @Override
    public List<Mensaje> getInbox(int idPersona, boolean all) {
        String top = "";
        if(!all){
            top = "TOP 15";
        }
        String sql = "SELECT " + top + " M.* FROM MENSAJERIA M WHERE M.PARAIDPERSONA = :idpersona "
                + "AND M.VISIBLE = 1 AND EXISTS (SELECT P.IDPERIODOACADEMICO "
                + "FROM PERIODOACADEMICO P WHERE P.VIGENTE = 1 AND "
                + "P.IDPERIODOACADEMICO = M.IDPERIODOACADEMICO) "
                + "ORDER BY M.FECHAENVIO DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpersona", idPersona);

        List<Mensaje> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getMensajeMapper());
        return list;
    }

    @Override
    public List<Mensaje> getOutbox(int idPersona) {

        String sql = "SELECT M.* FROM MENSAJERIA M WHERE M.DEIDPERSONA = :idpersona "
                + "AND M.VISIBLE = 1 AND EXISTS (SELECT P.IDPERIODOACADEMICO "
                + "FROM PERIODOACADEMICO P WHERE P.VIGENTE = 1 AND "
                + "P.IDPERIODOACADEMICO = M.IDPERIODOACADEMICO) "
                + "ORDER BY M.FECHAENVIO DESC";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpersona", idPersona);

        List<Mensaje> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getMensajeMapper());
        return list;
    }

    @Override
    public int getCantidadMensaje(int idpersona, boolean nuevo) {
        String sql = "SELECT COUNT(*) FROM MENSAJERIA M WHERE M.PARAIDPERSONA = :idpersona "
                + "AND M.VISIBLE = 1 AND M.ESTADOLEIDO = 0 AND EXISTS (SELECT P.IDPERIODOACADEMICO "
                + "FROM PERIODOACADEMICO P WHERE P.VIGENTE = 1 AND "
                + "P.IDPERIODOACADEMICO = M.IDPERIODOACADEMICO) ";
        if (nuevo) {
            //mensajes nuevos en los últimos 3 dias.
            sql += "AND DATEDIFF(DAY,M.FECHAENVIO,GETDATE()) <= :dias ";
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpersona", idpersona);
        params.addValue("dias", AppConstants.RESUMEN_DIAS);

        return this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
    }

    @Override
    public Mensaje getMensaje(int id) {
        String sql = "SELECT TOP 1 * FROM MENSAJERIA WHERE IDMENSAJERIA = :id "
                + "ORDER BY FECHAENVIO DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        Mensaje mensaje = (Mensaje) this.getNamedParameterJdbcTemplate().queryForObject(sql, params,
                UtilRowMapper.getMensajeMapper());
        return mensaje;
    }

    @Override
    @Transactional
    public void updateLeido(int id, boolean actualizaFechaLeido) {
        String sql = "UPDATE MENSAJERIA SET ESTADOLEIDO = :estadoleido ";
        if (actualizaFechaLeido) {
            sql += " ,FECHALEIDO = GETDATE() ";
        }
        sql += "WHERE IDMENSAJERIA = :id ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("estadoleido", true);

        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    @Transactional
    public void saveMensajeria(Mensaje mensaje) {
        String sql = "INSERT INTO MENSAJERIA(DEIDPERSONA, PARAIDPERSONA, ASUNTO, MENSAJE, "
                + "FECHAENVIO, VISIBLE, IDTIPOMENSAJE, IDPERIODOACADEMICO) "
                + "VALUES (:deIdPersona, :paraIdPersona, :asunto, :mensaje, "
                + "GETDATE(), :visible ,1, :idPeriodoAcademico)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("deIdPersona", mensaje.getPersonaDe().getId());
        params.addValue("paraIdPersona", mensaje.getPersonaPara().getId());
        params.addValue("asunto", mensaje.getAsunto());
        params.addValue("mensaje", mensaje.getMensaje());
        params.addValue("visible", true);
        params.addValue("idPeriodoAcademico", mensaje.getPeriodoAcademico().getId());

        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public List<Email> getEmailList(int idpersona) {
        String sql = "SELECT EP.IDTIPOEMAIL, EP.EMAIL "
                + "FROM PERSONAEMAIL EP WHERE EP.IDPERSONA = :idpersona AND EP.IDTIPOEMAIL IN(1,2)"
                + "ORDER BY EP.IDTIPOEMAIL ASC";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpersona", idpersona);

        List<Email> list = (ArrayList<Email>) this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getEmailMapper());
        return list;
    }
    
    @Override
    public List<Email> getEmailValidSendList(int idpersona) {
        String sql = "SELECT EP.IDTIPOEMAIL, EP.EMAIL "
                + "FROM PERSONAEMAIL EP WHERE EP.IDPERSONA = :idpersona AND "
                + "dbo.RegexMatch(EP.EMAIL,'^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$') = 1 ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpersona", idpersona);

        List<Email> list = (ArrayList<Email>) this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getEmailMapper());
        return list;
    }
    
    @Override
    public List<Email> getEmailValidSend(int idpersona) {
        String sql = "SELECT EP.IDTIPOEMAIL, EP.EMAIL "
                + "FROM PERSONAEMAIL EP WHERE EP.IDTIPOEMAIL = 1 AND EP.IDPERSONA = :idpersona AND "
                + "dbo.RegexMatch(EP.EMAIL,'^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$') = 1 ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idpersona", idpersona);

        List<Email> list = (ArrayList<Email>) this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getEmailMapper());
        if(list.isEmpty()){
            return null;
        }
        return list;
    }
}
