package com.udep.siga.dao.impl;

import com.udep.siga.bean.Articulo;
import com.udep.siga.bean.ArticuloCV;
import com.udep.siga.bean.AsociacionProfesionalCV;
import com.udep.siga.bean.CapacitacionCV;
import com.udep.siga.bean.CargoDirectivoCV;
import com.udep.siga.bean.ConsultoriaCv;
import com.udep.siga.bean.CursoDictadoCV;
import com.udep.siga.bean.DocenciaCV;
import com.udep.siga.bean.EmpresaCV;
import com.udep.siga.bean.EstanciaInvestigacionCV;
import com.udep.siga.bean.EstudioCV;
import com.udep.siga.bean.Evento;
import com.udep.siga.bean.IdiomasCV;
import com.udep.siga.bean.Libro;
import com.udep.siga.bean.LineaInvestigacion;
import com.udep.siga.bean.MeritoCV;
import com.udep.siga.bean.OtroTrabajoCV;
import com.udep.siga.bean.PatenteCV;
import com.udep.siga.bean.PremioCV;
import com.udep.siga.bean.ProyectoCV;
import com.udep.siga.bean.RedAcademicaCV;
import com.udep.siga.bean.ReunionEventoCV;
import com.udep.siga.bean.Tesis;
import com.udep.siga.bean.TrabajoInvestigacion;
import com.udep.siga.bean.enumeration.TipoGrado;
import com.udep.siga.bean.enumeration.TipoTesis;
import com.udep.siga.dao.CVDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("cvDAO")
public class ICVDAO extends CustomizeJdbcDaoSupport implements CVDAO {

    @Override
    public List<EstudioCV> getEstudiosCV(int idProfesor, int tipo) {

        String sql = "SELECT AÑO,TIPO,PAIS,GRADOTITULO,UNIVERSIDAD,TITULOTESIS "
                + "FROM ESTUDIOCV WHERE IDPERSONA = ? ";
        //pregrado
        if (tipo == TipoGrado.LICENCIADO.getId()) {
            sql += "AND TIPO = ? ORDER BY AÑO ASC ";
        } //posgrado
        else if (tipo > TipoGrado.LICENCIADO.getId()) {
            sql += "AND TIPO >= ? ORDER BY AÑO DESC ";
        }

        List<EstudioCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor, tipo}, UtilRowMapper.getEstudioCVMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<CapacitacionCV> getCapacitacionesCV(int idProfesor) {

        String sql = "SELECT AÑO,EVENTO,LUGAR,HORAS,INSTITUCION "
                + "FROM CAPACITACIONCV WHERE IDPERSONA = ? ORDER BY AÑO DESC ";

        List<CapacitacionCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getCapacitacionCVMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<LineaInvestigacion> getLineaInvestigacionByProfesor(int idProfesor) {

        String sql = "SELECT NOMBRE,DESCRIPCION FROM LINEAINVESTIGACION WHERE "
                + "IDLINEAINVESTIGACION IN (SELECT IDCAMPOINVESTIGACION FROM INVESTIGACIONCONCLUIDA "
                + "WHERE IDPROFESOR = ? AND ACTIVA = 1) OR IDLINEAINVESTIGACION IN "
                + "(SELECT IDCAMPOINVESTIGACION FROM TRABAJOINVESTIGACION WHERE ACTIVA = 1 "
                + "AND  IDPROFESOR = ?) AND ESLINEAINVESTIGACION = 0 ORDER BY NOMBRE ";

        List<LineaInvestigacion> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor,idProfesor}, UtilRowMapper.getLineaInvestigacionMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<TrabajoInvestigacion> getWorkingPaper(int idProfesor) {

        String sql = "SELECT TITULO,AUTORES FROM TRABAJOINVESTIGACION WHERE RESULTADO ='Artículo' "
                + "AND ADDREFERENCIACV = 1 AND IDPROFESOR = ? AND ACTIVA =1 ";

        List<TrabajoInvestigacion> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, new RowMapper() {
            public TrabajoInvestigacion mapRow(ResultSet rs, int rowNum) throws SQLException {
                TrabajoInvestigacion item = new TrabajoInvestigacion();
                item.setTitulo(rs.getString("TITULO"));
                item.setAutores(rs.getString("AUTORES"));
                return item;
            }
        });
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<Tesis> getTesisByProfesorYTipo(int idProfesor, int tipo) {

        String sql = "SELECT T.IDTESIS, T.TIPO, T.ASESOR, T.GRADOOBTENIDO, T.UNIVERSIDAD, "
                + "T.FECHAINICIO, T.FECHAFIN, T.FECHASUSTENTACION, ISNULL(T.ISBN,'') 'ISBN', ISNULL(T.PAGINAS,0) 'PAGINAS', "
                + "T.IDINVESTIGACIONGENERICA FROM TESIS T JOIN INVESTIGACIONGENERICA IG "
                + "ON IG.IDINVESTIGACIONGENERICA = T.IDINVESTIGACIONGENERICA "
                + "WHERE IG.IDPROFESOR = ? ";

        //pregrado
        if (tipo == TipoTesis.PREGRADO.getId()) {
            sql += "AND T.TIPO = ? ";
        } //posgrado
        else if (tipo >= TipoTesis.MAESTRIA.getId()) {
            sql += "AND T.TIPO >= ? ";
        }
        //sql += "ORDER BY T.IDTESIS DESC ";

        List<Tesis> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor, tipo}, UtilRowMapper.getTesisMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<EstanciaInvestigacionCV> getEstanciaInvestigaByProfesor(int idProfesor) {

        String sql = "SELECT TEMA, CENTROVISITADO, CIUDAD, PAIS, DESDE, HASTA, DESCRIPCION "
                + "FROM ESTANCIAINVESTIGACIONCV WHERE IDPROFESOR = ? ";

        List<EstanciaInvestigacionCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getEstanciaInvestigacionCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<ProyectoCV> getProyectosByProfesor(int idProfesor) {

        String sql = "SELECT NOMBRE, PARTICIPACION, AREA, CIUDAD, PAIS, DESDE, HASTALAFECHA, HASTA, "
                + "FINANCIAMIENTO, DESCRIPCION FROM PROYECTOCV WHERE IDPROFESOR = ? ";

        List<ProyectoCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getProyectoCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<ConsultoriaCv> getConsultoriasByProfesor(int idProfesor) {

        String sql = "SELECT CONSULTORIAEN,CLIENTE,PARTICIPANTE,CIUDAD,PAIS,DESDE, "
                + "HASTA,DESCRIPCION FROM CONSULTORIACV WHERE IDPROFESOR = ? ";

        List<ConsultoriaCv> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getConsultoriaCvMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<Evento> getEventoByProfesor(int idProfesor) {

        String sql = "SELECT IDEVENTO,TITULOPONENCIA,NUMEVENTO,NOMBRE,ORGANIZADOPOR, "
                + "AMBITO,DESCRIPCION,FECHA, CIUDAD, PAIS FROM EVENTO WHERE IDPROFESOR  = ? ";

        List<Evento> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getEventoMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<ReunionEventoCV> getReunionEventoByProfesor(int idProfesor) {

        String sql = "SELECT NOMBRE,ANIO,ORGANIZADOPOR,CIUDAD,PAIS,AMBITO,DESCRIPCION "
                + "FROM REUNIONEVENTOCV WHERE IDPROFESOR  = ? ";

        List<ReunionEventoCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getReunionEventoCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<Libro> getLibrosByProfesorYTipo(int idProfesor, boolean isColectivo) {

        String sql = "SELECT L.IDLIBRO,L.LIBROCOLECTIVO, L.IDINVESTIGACIONGENERICA, "
                + "L.COORDINADOR, L.COLABORADORES, L.SERIECOLECCION, L.EDITORIAL, "
                + "L.ISBN, L.EDICION, L.PAGINAS, L.FECHAPUBLICACION, L.CIUDAD, "
                + "L.PAIS, L.OTRASVERSIONES FROM LIBRO L WHERE "
                + "L.IDINVESTIGACIONGENERICA IN (SELECT IDINVESTIGACIONGENERICA "
                + "FROM INVESTIGACIONGENERICA WHERE IDPROFESOR = ? ) ";

        //pregrado
        if (isColectivo) {
            sql += "AND L.LIBROCOLECTIVO = 1 ";
        } //posgrado
        else {
            sql += "AND L.LIBROCOLECTIVO = 0 ";
        }

        List<Libro> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getLibroMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<Articulo> getArticulosByProfesorYMedio(int idProfesor, String medio) {

        String sql = "SELECT A.IDARTICULO, A.IDTIPOARTICULO, A.COLABORADORES, A.MEDIOPUBLICACION, "
                + "A.NOMBREMEDIO, A.INDEXADA, A.PUBLICADOACTA, A.ISBN, A.ACTA, A.ISSN, A.EDICION, "
                + "A.TOMOVOLUMEN, A.NUMEROFASCICULO, A.PAGINAINICIO, A.PAGINAFIN, A.DOI, "
                + "A.FECHAPUBLICACION, A.CIUDADPUBLICACION, A.PAISPUBLICACION, A.PROCESO, "
                + "A.OTRAVERSIONPUBLICADA, A.IDINVESTIGACIONGENERICA FROM ARTICULO A "
                + "WHERE A.MEDIOPUBLICACION LIKE ? "
                + "AND A.IDINVESTIGACIONGENERICA IN (SELECT IDINVESTIGACIONGENERICA "
                + "FROM INVESTIGACIONGENERICA WHERE IDPROFESOR = ? ) AND PUBLICADOACTA = 0";

        List<Articulo> list = this.getJdbcTemplate().query(sql,
                new Object[]{medio, idProfesor}, UtilRowMapper.getArticuloMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<Articulo> getArticulosByProfesorConActa(int idProfesor) {

        String sql = "SELECT A.IDARTICULO, A.IDTIPOARTICULO, A.COLABORADORES, A.MEDIOPUBLICACION, "
                + "A.NOMBREMEDIO, A.INDEXADA, A.PUBLICADOACTA, A.ISBN, A.ACTA, A.ISSN, A.EDICION, "
                + "A.TOMOVOLUMEN, A.NUMEROFASCICULO, A.PAGINAINICIO, A.PAGINAFIN, A.DOI, "
                + "A.FECHAPUBLICACION, A.CIUDADPUBLICACION, A.PAISPUBLICACION, A.PROCESO, "
                + "A.OTRAVERSIONPUBLICADA, A.IDINVESTIGACIONGENERICA FROM ARTICULO A "
                + "WHERE A.IDINVESTIGACIONGENERICA IN (SELECT IDINVESTIGACIONGENERICA "
                + "FROM INVESTIGACIONGENERICA WHERE IDPROFESOR = ? ) AND PUBLICADOACTA = 1";

        List<Articulo> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getArticuloMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<ArticuloCV> getArticulosCVByProfesor(int idProfesor) {

        String sql = "SELECT ANIO,TITULO,MEDIOPUBLICACION,PAGINAINICIO,PAGINAFIN "
                + "FROM ARTICULOCV WHERE IDPROFESOR = ?";

        List<ArticuloCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getArticuloCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<DocenciaCV> getDocenciaCVByProfesor(int idProfesor) {

        String sql = "SELECT IDDOCENCIACV, CENTROESTUDIOS, HASTALAFECHA, AÑOI, AÑOF "
                + "FROM DOCENCIACV WHERE IDPERSONA = ? ORDER BY AÑOI DESC";

        List<DocenciaCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getDocenciaCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            for (DocenciaCV docencia : list) {
                docencia.setCursos(this.getCursoCVList(docencia.getId()));
            }
            return list;
        }
    }

    @Override
    public List<CursoDictadoCV> getCursoCVList(int idDocencia) {

        String sql = "SELECT ASIGNATURA,PERIODO,CATEGORIA,ESTUDIO "
                + "FROM CURSODICTADOCV WHERE IDDOCENCIACV = ? ORDER BY PERIODO DESC";

        List<CursoDictadoCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idDocencia}, UtilRowMapper.getCursoDictadoCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    //EXPERIENCIA PROFESIONAL
    @Override
    public List<OtroTrabajoCV> getOtrosTrabajosList(int idProfesor) {

        String sql = "SELECT NOMBRE,EMPRESA,DIAI,MESI,AÑOI,DIAF,MESF,AÑOF,HASTALAFECHA "
                + "FROM OTROTRABAJOCV where IDPERSONA = ? ORDER BY AÑOI DESC";

        List<OtroTrabajoCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getOtroTrabajoCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<CargoDirectivoCV> getCargoDirectivoList(int idProfesor) {

        String sql = "SELECT CARGO,INSTITUCION,DIAI,MESI,AÑOI,DIAF,MESF,AÑOF, "
                + "HASTALAFECHA FROM CARGODIRECTIVOCV WHERE IDPERSONA = ? ORDER BY AÑOI DESC";

        List<CargoDirectivoCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getCargoDirectivoCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<EmpresaCV> getOtrosCargoList(int idProfesor) {

        String sql = "SELECT CARGO,CENTRODETRABAJO,DIAI,MESI,AÑOI,DIAF,MESF,AÑOF, "
                + "HASTALAFECHA FROM EMPRESACV WHERE IDPERSONA = ? ORDER BY AÑOI DESC";

        List<EmpresaCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getEmpresaCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    //ASOCIACIÓN Y REDES A LA QUE PERTENECE
    @Override
    public List<RedAcademicaCV> getRedesAcademicasList(int idProfesor) {

        String sql = "SELECT NOMBRE,URL,ESPECIALIZACION FROM REDACADEMICACV "
                + "WHERE IDPERSONA = ? ORDER BY NOMBRE ASC";

        List<RedAcademicaCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getRedAcademicaCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<AsociacionProfesionalCV> getAsociacionProfesionalList(int idProfesor) {

        String sql = "SELECT NOMBRE,PUESTO,DIAI,MESI,AÑOI,DIAF,MESF,AÑOF,HASTALAFECHA "
                + "FROM ASOCIACIONPROFESIONALCV WHERE IDPERSONA = ? ORDER BY AÑOI DESC";

        List<AsociacionProfesionalCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getAsociacionProfesionalCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    //IDIOMAS
    @Override
    public List<IdiomasCV> getIdiomasList(int idProfesor, boolean titulo) {

        String sql = "SELECT NOMBRE,LUGAR,AÑOI,NIVELC,NIVELL,NIVELE "
                + "FROM IDIOMACV WHERE IDPERSONA = ? ";
        if (titulo) {
            sql += "AND ESTITULO = 1 ";
        } else {
            sql += "AND ESTITULO = 0 ";
        }        
        sql += "ORDER BY AÑOI DESC ";

        List<IdiomasCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getIdiomasCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
    
    //PATENTES Y/O REGISTRO DE PROPIEDAD
    @Override
    public List<PatenteCV> getPatenteList(int idProfesor) {

        String sql = "SELECT AÑO,TEMA FROM PATENTECV "
                + "WHERE IDPERSONA = ? ORDER BY AÑO DESC ";
        

        List<PatenteCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getPatenteCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
    
    //PREMIOS, HONORES Y BECAS
    @Override
    public List<PremioCV> getPremiosList(int idProfesor) {

        String sql = "SELECT AÑO,PREMIO,ENTIDAD FROM PREMIOCV "
                + "WHERE IDPERSONA = ? ORDER BY AÑO DESC ";
        

        List<PremioCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getPremioCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
    
    //OTROS MÉRITOS
    @Override
    public List<MeritoCV> getOtrosMeritosList(int idProfesor) {

        String sql = "SELECT AÑO, MERITO FROM MERITOCV "
                + "WHERE IDPERSONA = ? ORDER BY AÑO DESC ";
        

        List<MeritoCV> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getMeritoCVMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
    
}
