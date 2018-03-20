package com.udep.siga.dao;

import com.udep.siga.bean.Articulo;
import com.udep.siga.bean.HorarioAsesoria;
import com.udep.siga.bean.Interaccion;
import com.udep.siga.bean.Libro;
import com.udep.siga.bean.Profesor;
import com.udep.siga.bean.Tesis;
import com.udep.siga.bean.TrabajoInvestigacion;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wilfredo Atoche
 */
public interface ProfesorDAO {
    public Map<String, Object> getInfoAcademico(int idProfesor);
    public List<HorarioAsesoria> getHorarioAsesoriaList(int idProfesor);
    public List<Interaccion> getAsesoriaInteraccionList(int idAlumno, int idAsesor);
    public List<Date> getAsesoriaFuturaList(int idAlumno, int idAsesor);
    public List<Profesor> findByNombreApellidos(String nombre, String apePaterno, String apeMaterno);
    public List<TrabajoInvestigacion> getTrabajosInvestigacionByProfesor(int idProfesor) ;
    public List<Articulo> getArtByProfesor(int idProfesor);
    public List<Libro> getLibrosByProfesor(int idProfesor);
    public List<Tesis> getTesisByProfesor(int idProfesor);
    public TrabajoInvestigacion getDetalleTrabajosInvestigacion(int idTrabajo);
}
