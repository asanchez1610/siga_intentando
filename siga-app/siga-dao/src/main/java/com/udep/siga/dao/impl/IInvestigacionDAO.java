package com.udep.siga.dao.impl;

import com.udep.siga.bean.ArchivoInvestigacion;
import com.udep.siga.bean.Articulo;
import com.udep.siga.bean.Capitulo;
import com.udep.siga.bean.DocumentosInvestigacion;
import com.udep.siga.bean.Evento;
import com.udep.siga.bean.InvestigacionGenerica;
import com.udep.siga.bean.Libro;
import com.udep.siga.bean.Tesis;
import com.udep.siga.dao.InvestigacionDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("investigacionDAO")
public class IInvestigacionDAO extends CustomizeJdbcDaoSupport implements InvestigacionDAO {

    @Override
    public InvestigacionGenerica getInvestigacionGenerica(int idInvestigacion) {
        String sql = "SELECT I.IDINVESTIGACIONGENERICA, I.TITULO, ISNULL(I.IDCAMPOINVESTIGACION,0) "
                + "'IDCAMPOINVESTIGACION', ISNULL(I.IDLINEAINVESTIGACION,0) 'IDLINEAINVESTIGACION', "
                + "I.ESTADO, ISNULL(I.IDESTUDIO,0) 'IDESTUDIO', I.IDDEPAACADEMICO, DA.NOMBRE 'DEPARTAMENTO', "
                + "I.IDAREAINVESTIGACION, AI.NOMBRE 'AREA', I.AUTOR, C.IDCENTROACADEMICO, C.NOMBRE 'CENTRO', "
                + "I.NUMDEPOSITOLEGAL, I.RESUMEN, I.ABSTRACT, I.PALABRACLAVE, ISNULL(I.RESTRICCIONPUBLICACION,0) "
                + "'RESTRICCIONPUBLICACION', I.EMBARGOCOMERCIALVENC, ISNULL(I.EMBARGOPERSONAL,0) 'EMBARGOPERSONAL', "
                + "I.TITULARDERECHOS, I.DERECHOS, I.URLLICENCIADERECHO, I.TITULOALT FROM INVESTIGACIONGENERICA I, "
                + "DEPAACADEMICO DA, AREAINVESTIGACION AI, CENTROACADEMICO C "
                + "WHERE DA.IDDEPAACADEMICO = I.IDDEPAACADEMICO AND AI.IDAREAINVESTIGACION = I.IDAREAINVESTIGACION "
                + "AND DA.IDCENTROACADEMICO = C.IDCENTROACADEMICO AND I.IDINVESTIGACIONGENERICA = ? ";

        List<InvestigacionGenerica> list = this.getJdbcTemplate().query(sql,
                new Object[]{idInvestigacion}, UtilRowMapper.getInvestigacionGenericaMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            list.get(0).getCampoInvestigacion().setNombre(this.getNombreLineaInvestigacion
                    (list.get(0).getCampoInvestigacion().getId()));
            if (list.get(0).getLineaInvestigacion() != null && list.get(0).getLineaInvestigacion().getId() != 0) {
                list.get(0).getLineaInvestigacion().setNombre(this.getNombreLineaInvestigacion
                        (list.get(0).getLineaInvestigacion().getId()));
            }
            if (list.get(0).getEstudio() != null && list.get(0).getEstudio().getId() != 0) {
                sql = "SELECT NOMBRE FROM ESTUDIO WHERE IDESTUDIO = ?";
                String estudio = this.getJdbcTemplate().queryForObject
                        (sql, new Object[]{list.get(0).getEstudio().getId()}, String.class);
                list.get(0).getEstudio().setNombre(estudio);
            }
            list.get(0).setDocumentos(this.getDocumentosInvestigacionList(idInvestigacion));
            return list.get(0);
        }
    }

    @Override
    public String getNombreLineaInvestigacion(int idLineaInvestigacion) {
        String sql = "SELECT NOMBRE FROM LINEAINVESTIGACION WHERE IDLINEAINVESTIGACION = ? ";

        List<String> list = this.getJdbcTemplate().queryForList(sql,
                new Object[]{idLineaInvestigacion}, String.class);
        if (list.isEmpty()) {
            return "";
        } else {
            return list.get(0);
        }
    }

    @Override
    public Articulo getArticulo(int idArticulo) {
        String sql = "SELECT A.IDARTICULO, A.IDTIPOARTICULO, A.COLABORADORES, A.MEDIOPUBLICACION, "
                + "A.NOMBREMEDIO, A.INDEXADA, A.PUBLICADOACTA, A.ISBN, A.ACTA, A.ISSN, A.EDICION, "
                + "A.TOMOVOLUMEN, A.NUMEROFASCICULO, A.PAGINAINICIO, A.PAGINAFIN, A.DOI, "
                + "A.FECHAPUBLICACION, A.CIUDADPUBLICACION, A.PAISPUBLICACION, A.PROCESO, "
                + "A.OTRAVERSIONPUBLICADA, A.IDINVESTIGACIONGENERICA FROM ARTICULO A WHERE A.IDARTICULO = ? ";

        List<Articulo> list = this.getJdbcTemplate().query(sql,
                new Object[]{idArticulo}, UtilRowMapper.getArticuloMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            list.get(0).setInvestigacionGenerica(this.getInvestigacionGenerica
                    (list.get(0).getInvestigacionGenerica().getId()));
            list.get(0).getInvestigacionGenerica().setEvento(this.getEvento
                    (list.get(0).getInvestigacionGenerica().getId()));
            return list.get(0);
        }
    }

    @Override
    public Evento getEvento(int idInvestigacion) {
        String sql = "SELECT TOP 1 IDEVENTO, TITULOPONENCIA, NUMEVENTO, NOMBRE, "
                + "ORGANIZADOPOR, AMBITO, DESCRIPCION, FECHA, CIUDAD, PAIS "
                + "FROM EVENTO WHERE IDINVESTIGACIONGENERICA = ? ";

        List<Evento> list = this.getJdbcTemplate().query(sql,
                new Object[]{idInvestigacion}, UtilRowMapper.getEventoMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public Tesis getTesis(int idTesis) {
        String sql = "SELECT T.IDTESIS, T.TIPO, T.ASESOR, T.GRADOOBTENIDO, T.UNIVERSIDAD, "
                + "T.FECHAINICIO, T.FECHAFIN, T.FECHASUSTENTACION, ISNULL(T.ISBN,'') 'ISBN', ISNULL(T.PAGINAS,0) 'PAGINAS', "
                + "T.IDINVESTIGACIONGENERICA FROM TESIS T WHERE  T.IDTESIS = ? ";

        List<Tesis> list = this.getJdbcTemplate().query(sql,
                new Object[]{idTesis}, UtilRowMapper.getTesisMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            list.get(0).setInvestigacionGenerica(this.getInvestigacionGenerica
                    (list.get(0).getInvestigacionGenerica().getId()));
            return list.get(0);
        }
    }
    
    @Override
    public Libro getLibro(int idLibro) {
        String sql = "SELECT L.IDLIBRO,L.LIBROCOLECTIVO, L.IDINVESTIGACIONGENERICA, "
                + "L.COORDINADOR, L.COLABORADORES, L.SERIECOLECCION, L.EDITORIAL, "
                + "L.ISBN, L.EDICION, L.PAGINAS, L.FECHAPUBLICACION, L.CIUDAD, "
                + "L.PAIS, L.OTRASVERSIONES FROM LIBRO L WHERE L.IDLIBRO = ? ";

        List<Libro> list = this.getJdbcTemplate().query(sql,
                new Object[]{idLibro}, UtilRowMapper.getLibroMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            list.get(0).setInvestigacionGenerica(this.getInvestigacionGenerica
                    (list.get(0).getInvestigacionGenerica().getId()));
             list.get(0).getInvestigacionGenerica().setEvento(this.getEvento
                    (list.get(0).getInvestigacionGenerica().getId()));
             if(list.get(0).isColectivo()){
                list.get(0).setCapitulos(this.getCapituloLibro(idLibro));
             }
            return list.get(0);
        }
    }
    
    @Override
    public List<Capitulo> getCapituloLibro(int idLibro) {
        String sql = "SELECT NUMERO, NOMBRE, PAGINAINICIO, PAGINAFIN "
                + "FROM CAPITULOLIBRO WHERE IDLIBRO = ? ";

        List<Capitulo> list = this.getJdbcTemplate().query(sql,
                new Object[]{idLibro}, UtilRowMapper.getCapituloMapper());
        if (list.isEmpty()) {
            return null;
        } 
        
        return list;        
    }
    
    @Override
    public List<DocumentosInvestigacion> getDocumentosInvestigacionList(int idInvestigacion) {
        String sql = "SELECT NOMBRE, IDIOMA, FORMATO, PROGRAMA, COMENTARIO "
                + "FROM DOCUMENTOINVESTIGACION WHERE IDINVESTIGACIONGENERICA = ? ";

        List<DocumentosInvestigacion> list = this.getJdbcTemplate().query(sql,
                new Object[]{idInvestigacion}, UtilRowMapper.getDocumentosInvestigacionMapper());
        if (list.isEmpty()) {
            return null;
        } 
        
        return list;        
    }
    
    @Override
    public List<ArchivoInvestigacion> getArchivoInvestigacionList(int idTrabajo) {
        String sql = "SELECT NOMBRE, DESCRIPCION, FECHASUBIDA FROM ARCHIVOINVESTIGACION "
                + "WHERE VISIBLEA = 1 AND IDTRABAJOINVESTIGACION = ? ";

        List<ArchivoInvestigacion> list = this.getJdbcTemplate().query(sql,
                new Object[]{idTrabajo}, UtilRowMapper.getArchivoInvestigacionMapper());
        if (list.isEmpty()) {
            return null;
        } 
        
        return list;        
    }
    
}
