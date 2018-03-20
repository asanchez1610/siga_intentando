package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AsignaturaHistorial;
import com.udep.siga.bean.CLCCategActividadDeportivaDest;
import com.udep.siga.bean.CLCCategActividadDisney;
import com.udep.siga.bean.CLCCategActividadInvestigacion;
import com.udep.siga.bean.CLCCategActividadProyectoSocial;
import com.udep.siga.bean.CLCCategAsigIntercambioEstud;
import com.udep.siga.bean.CLCCategCapacitacionProfesional;
import com.udep.siga.bean.CLCCategEstudioDirigido;
import com.udep.siga.bean.CLCCategPractPreProfesional;
import com.udep.siga.bean.CLCCategoriaActividad;
import com.udep.siga.bean.CLCCategoriaAlumno;
import com.udep.siga.bean.CLCCategoriaIdioma;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface CLCCategoriaDAO {
    public CLCCategoriaAlumno getCategoriaCLC(int idCategoria );
    
    public List<CLCCategoriaActividad> getCategActividadArtisticaDestacada
            (int idAlumno, int idEdicionesEstudio);
    public List<CLCCategActividadInvestigacion> getCategActividadInvestigacion
            (int idAlumno, int idEdicionesEstudio);    
    public List<CLCCategActividadProyectoSocial> getCategActividadProyeSocial
            (int idAlumno, int idEdicionesEstudio,int tipoVoluntariado);
    public List<CLCCategActividadDeportivaDest> getCategActividadDeportivaDest
            (int idAlumno, int idEdicionesEstudio);
    public List<CLCCategoriaActividad> getCategActividadExtraConvenio
            (int idAlumno, int idEdicionesEstudio);
    public List<CLCCategActividadDisney> getCategActividadDisney
            (int idAlumno, int idEdicionesEstudio);
    public List<CLCCategAsigIntercambioEstud> getCategAsigIntercambioEstud
            (int idAlumno, int idEdicionesEstudio);
    public List<CLCCategCapacitacionProfesional> getCategCapacitacionProfesional
            (int idAlumno, int idEdicionesEstudio);
    public List<CLCCategEstudioDirigido> getCategEstudioDirigido
            (int idAlumno, int idEdicionesEstudio);
    public List<CLCCategoriaIdioma> getCategoriaIdioma
            (int idAlumno, int idEdicionesEstudio);
    public List<CLCCategPractPreProfesional> getCategHorasPractProfesionales
            (int idAlumno, int idEdicionesEstudio);
    public List<AsignaturaHistorial> getClcOtrosPlanesDeEstudio
            (int idAlumno, int idEdicionesEstudio);
    
    public float getCLCObtenidoByAlumnoCateg(AlumnoEstudio alumnoEstudio, String tablaCLC);
    public float getCLCObtenidoByAlumnoProySocial
            (AlumnoEstudio alumnoEstudio, int tipoVoluntariado );
    public int getCLCObtenidoByAlumnoDeportivaDest
            (AlumnoEstudio alumnoEstudio, int maxValor );
    public int getCLCObtenidoByOtrosPlanesDeEstudio (AlumnoEstudio alumnoEstudio);
    public int getCLCObtenidoByAlumnoIntercambioEst(AlumnoEstudio alumnoEstudio );
    public int getCLCObtenidoByIdiomas(AlumnoEstudio alumnoEstudio, int maxValor );
    
}
