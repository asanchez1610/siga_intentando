package com.udep.siga.dao;

import com.udep.siga.bean.enumeration.Campus;
import java.util.Map;

/**
 *
 * @author Gary Ayala
 */
public interface WebServiceArgusDAO {
    public Map<String, Object> infoAlumnoEstudio(Campus campus, String carne, int edicionEstudioId);
    public String infoAlumnoTipoBeca(Campus campus, String carne, int edicionEstudioId,
        int periodoAcademicoId);
    public Map<String, Object> infoAlumnoHistorialIdiomas(Campus campus, String carne);
    public Map<String, Object> infoAlumnoCronogramaPagos(Campus campus, String carne, int edicionEstudioId,
        int periodoAcademicoId);
    public Map<String, Object> infoAlumnoCuotaPagos(int cpgp,int idcampus);
    public Map<String, Object> infoAlumnoRecibodePagos(int nrecibo,int idcampus);
    public Map<String, Object> infoAlumnoReceptorCorrespondencia(Campus campus, String carne);
    public Map<String, Object> infoAlumnoFamiliaMiembroById(Campus campus, String carne, int tipoMiembroId);
    public boolean infoAlumnoFamiliaMiembroUpdateById(Campus campus, int id, 
        String email, String direccion, String telfijo, String telcelular);
    public boolean isMoroso(Campus campus, String carne, int edicionEstudioId);
    public boolean tieneDeudaMatricula(Campus campus, String carne, int programaId);
}
