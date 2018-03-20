package com.udep.siga.dao;

import com.udep.siga.bean.ArchivoInvestigacion;
import com.udep.siga.bean.Articulo;
import com.udep.siga.bean.Capitulo;
import com.udep.siga.bean.DocumentosInvestigacion;
import com.udep.siga.bean.Evento;
import com.udep.siga.bean.InvestigacionGenerica;
import com.udep.siga.bean.Libro;
import com.udep.siga.bean.Tesis;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface InvestigacionDAO {

    public InvestigacionGenerica getInvestigacionGenerica(int idInvestigacion);
    public String getNombreLineaInvestigacion(int idLineaInvestigacion);
    public Articulo getArticulo(int idArticulo);
    public Evento getEvento(int idInvestigacion);
    public Tesis getTesis(int idTesis);
    public Libro getLibro(int idLibro);
    public List<Capitulo> getCapituloLibro(int idLibro);
    public List<DocumentosInvestigacion> getDocumentosInvestigacionList(int idInvestigacion);
    public List<ArchivoInvestigacion> getArchivoInvestigacionList(int idTrabajo);
}
