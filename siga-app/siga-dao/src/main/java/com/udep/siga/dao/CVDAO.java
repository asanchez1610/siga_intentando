package com.udep.siga.dao;

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
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface CVDAO {    
    public List<EstudioCV> getEstudiosCV(int idProfesor, int tipo);
    public List<CapacitacionCV> getCapacitacionesCV(int idProfesor);
    public List<LineaInvestigacion> getLineaInvestigacionByProfesor(int idProfesor);
    public List<TrabajoInvestigacion> getWorkingPaper(int idProfesor);
    public List<Tesis> getTesisByProfesorYTipo(int idProfesor, int tipo);
    public List<EstanciaInvestigacionCV> getEstanciaInvestigaByProfesor(int idProfesor);
    public List<ProyectoCV> getProyectosByProfesor(int idProfesor);
    public List<ConsultoriaCv> getConsultoriasByProfesor(int idProfesor);
    public List<Evento> getEventoByProfesor(int idProfesor) ;
    public List<ReunionEventoCV> getReunionEventoByProfesor(int idProfesor);
    public List<Libro> getLibrosByProfesorYTipo(int idProfesor, boolean isColectivo);
    public List<Articulo> getArticulosByProfesorYMedio(int idProfesor, String medio);
    public List<Articulo> getArticulosByProfesorConActa(int idProfesor);
    public List<ArticuloCV> getArticulosCVByProfesor(int idProfesor);    
    public List<DocenciaCV> getDocenciaCVByProfesor(int idProfesor);
    public List<CursoDictadoCV> getCursoCVList(int idDocencia);
    public List<OtroTrabajoCV> getOtrosTrabajosList(int idProfesor) ;
    public List<CargoDirectivoCV> getCargoDirectivoList(int idProfesor);
    public List<EmpresaCV> getOtrosCargoList(int idProfesor);
    public List<RedAcademicaCV> getRedesAcademicasList(int idProfesor);
    public List<AsociacionProfesionalCV> getAsociacionProfesionalList(int idProfesor);
    public List<IdiomasCV> getIdiomasList(int idProfesor, boolean titulo);
    public List<PatenteCV> getPatenteList(int idProfesor);
    public List<PremioCV> getPremiosList(int idProfesor);
    public List<MeritoCV> getOtrosMeritosList(int idProfesor);
}
