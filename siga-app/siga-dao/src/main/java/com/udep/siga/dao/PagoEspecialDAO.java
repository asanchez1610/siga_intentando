package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.FechaCuotaPagoEspecial;
import com.udep.siga.bean.PensionAlumno;
import com.udep.siga.bean.enumeration.TipoPagoEspecial;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrador
 */
public interface PagoEspecialDAO {
    public TipoPagoEspecial getTipoPagosEspecialesByAlumno(List<PensionAlumno> apLista);
    public boolean isActivo(int idPeriodoAcademico, int idCampus);
    public boolean isProcesadoPagosEspecialesByAlumno
            (AlumnoEstudio alumnoEstudio, List<PensionAlumno> apLista,boolean procesado);
    public List<TipoPagoEspecial> getTipoPagoEspecialList();
    public TipoPagoEspecial getTipoPagoEspecial(int id);
    public void saveFechaCuotaPagoEspecial(int idAlumno, int idPagoEspecial, int idTipoPagoEspecial);
    public void saveEnPensiones(AlumnoEstudio alumnoEstudio,List<PensionAlumno> apLista,
    boolean isEspecial, boolean inQuincena);
    public Map<String, Object> getAlumnoPagosEspecialesPensiones(AlumnoEstudio alumnoEstudio,
            List<PensionAlumno> apLista,boolean procesado, boolean isProcesado);
    public String getCaluCadena(List<PensionAlumno> apLista);
    public boolean isPagoEspecialProcesado(int idCampus, String caluCadena, boolean procesado);
    public List<FechaCuotaPagoEspecial> getFechaCuotaPagoEspecial(int idPeriodo,int idCampus,int idTipo);
}
