package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.PagoCuota;
import com.udep.siga.bean.PensionAlumno;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.dao.PensionesDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("pensionesDAO")
public class IPensionesDAO extends CustomizeJdbcDaoSupport implements PensionesDAO {

    private JdbcTemplate jdbcTemplateAux;
    @Autowired
    @Qualifier("dataSourcePPiura")
    private DataSource dataSourcePPiura;
    @Autowired
    @Qualifier("dataSourcePLima")
    private DataSource dataSourcePLima;

    public List<PagoCuota> getCronogramaPagosPensionesList(String carne, int idEdicionEstudio, int idCampus, int idPeriodoAcademico) {
            if (idCampus == Campus.Piura.getId()) {
                jdbcTemplateAux = new JdbcTemplate(dataSourcePPiura);
            } else if (idCampus == Campus.Lima.getId()) {
                jdbcTemplateAux = new JdbcTemplate(dataSourcePLima);
            }
        
            String cpac = this.getCpacString(idEdicionEstudio);

            String sql = "SELECT PP.CPGP AS ID, EC.CSTC AS ESTADO, "
                    + "PP.MPGPNUM AS CUOTA, PP.DPGPFEP AS FECHA, PP.SPGPIMP AS IMPORTE "
                    + "FROM PAGO_PENSIONES AS PP, "
                    + "ESTADO_CUOTA AS EC, ALUMNO AS A "
                    + "WHERE A.NALUCAR = ? AND A.LALUANU = 0 AND A.CPAC IN " + cpac + " "
                    + "AND A.CSEM = ? AND EC.CSTC = PP.CSTC AND A.CALU = PP.CALU AND PP.LPGPANU = 0 "
                    + "ORDER BY PP.MPGPNUM ASC";

            System.out.println("carne:"+carne);
            System.out.println("cpac:"+cpac);
            System.out.println("this.getCsem(idCampus, idPeriodoAcademico):"+this.getCsem(idCampus, idPeriodoAcademico));
            List<PagoCuota> list = jdbcTemplateAux.query(sql,
                    new Object[]{carne, this.getCsem(idCampus, idPeriodoAcademico)},
                    UtilRowMapper.getPensionesPagosCuotasMapper());

            return list;
    }
    
    public List<PensionAlumno> getPensionesListByAlumno(AlumnoEstudio alumnoEstudio) {
            if (alumnoEstudio.getCampus().getId() == Campus.Piura.getId()) {
                jdbcTemplateAux = new JdbcTemplate(dataSourcePPiura);
            } else if (alumnoEstudio.getCampus().getId() == Campus.Lima.getId()) {
                jdbcTemplateAux = new JdbcTemplate(dataSourcePLima);
            }
        
            short csem = this.getCsem(alumnoEstudio.getCampus().getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId());
            String cpac = this.getCpacString(alumnoEstudio.getEdicionestudio().getId());
            
            String sql = "SELECT A.CALU,A.LALUESP, A.LALUPQU FROM ALUMNO A WHERE A.NALUCAR = ? AND A.LALUANU = 0 "
                    + "AND A.CSEM = ? AND A.CPAC IN " + cpac;

            List<PensionAlumno> list = jdbcTemplateAux.query(sql,
                    new Object[]{alumnoEstudio.getCarne(), csem},UtilRowMapper.getPensionAlumnoMapper());

            return list;
    }

    /* 
     * Obtener PeriodoPensiones
     */
    public short getCsem(int idCampus, int idPeriodoAcademico) {

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

    public String getCpacString(int idEdicionEstudio) {
        String cadena = " (0";
        List<Byte> cpacLista = new ArrayList<Byte>(0);
        for (byte cpac : this.getCpac(idEdicionEstudio)) {

            if (cpac != 0 && !cpacLista.contains(cpac)) {
                cpacLista.add(cpac);
            }

        }

        for (int i = 0; cpacLista.size() > i; i++) {

            if (cpacLista.get(i) != null) {
                cadena += ", " + cpacLista.get(i);
            }
        }

        cadena += ")";

        return cadena;
    }

    /* 
     * Obtener CPAC apartir del idEdicionEstudio
     */
    private byte[] getCpac(int idEdicionEstudio) {

        byte[] ids = new byte[1];

        switch (idEdicionEstudio) {
            case 2:
                ids[0] = 7;
                break;
            case 3:
                ids[0] = 5;
                break;
            case 6:
                ids[0] = 1;
                break;
            case 11:
                ids[0] = 11;
                break;
            case 14:
                ids[0] = 4;
                break;
            case 22:
                ids[0] = 10;
                break;
            case 34:
                ids[0] = 29;
                break;
            case 35:
                ids[0] = 30;
                break;
            case 36:
                ids[0] = 31;
                break;
            case 37:
                ids[0] = 32;
                break;
            case 38:
                ids[0] = 33;
                break;
            case 39:
                ids[0] = 34;
                break;
                
            case 133:
                ids[0] = 39;
                break;    
            
            case 117:
                ids[0] = 40;
                break;  
                 
            case 80:
                ids[0] = 13;
                break;

            case 4:
                ids = new byte[2];
                ids[0] = 37;
                ids[1] = 17;
                break;

            case 5:
                ids = new byte[2];
                ids[0] = 2;
                ids[1] = 15;
                break;

            case 7:
                ids = new byte[2];
                ids[0] = 3;
                ids[1] = 16;
                break;

            case 1:
                ids = new byte[2];
                ids[0] = 6;
                ids[1] = 18;
                break;

            case 10:
                ids = new byte[2];
                ids[0] = 6;
                ids[1] = 18;
                break;

            case 9:
                ids = new byte[2];
                ids[0] = 9;
                ids[1] = 19;
                break;

            case 24:
                ids = new byte[4];
                ids[0] = 5;
                ids[1] = 6;
                ids[2] = 7;
                ids[3] = 18;
                break;

            default:
                ids[0] = 0;
                break;
        }

        return ids;

    }
}
