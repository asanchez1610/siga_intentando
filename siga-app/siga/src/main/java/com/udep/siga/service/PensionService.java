package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.CronogramaPago;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.FechaCuotaPagoEspecial;
import com.udep.siga.bean.PensionAlumno;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.TipoPagoEspecial;
import com.udep.siga.dao.MensajeriaDAO;
import com.udep.siga.dao.PagoEspecialDAO;
import com.udep.siga.dao.PensionesDAO;
import com.udep.siga.dao.UtilDAO;
import com.udep.siga.dao.WebServiceArgusDAO;
import com.udep.siga.util.AppConstants;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrador
 */
@Service("pensionService")
public class PensionService {

    @Autowired
    private PensionesDAO pensionesDAO;
    @Autowired
    private PagoEspecialDAO pagoEspecialDAO;
    @Autowired
    private MensajeriaDAO mensajeriaDAO;
    @Autowired
    private MailService mailService;
    @Autowired
    private UtilDAO utilDAO;
    @Autowired
    private UsuarioService usuarioService;
    private @Autowired WebServiceArgusDAO webServiceArgusDAO;

    public CronogramaPago getCronogramaPagosPensionesList(AlumnoEstudio alumnoEstudio) {
        CronogramaPago cronogramaPago = new CronogramaPago();
        cronogramaPago.setPeriodoAcademico(alumnoEstudio.getPeriodoAcademicoVigente().getNombre());
        cronogramaPago.setPagoCuotaList(pensionesDAO.getCronogramaPagosPensionesList(alumnoEstudio.getCarne(), alumnoEstudio.getEdicionestudio().getId(),
                alumnoEstudio.getCampus().getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId()));

        return cronogramaPago;
    }

    public boolean isActivoPagoEspecial(int idPeriodoAcademico, int idCampus){
        
        return pagoEspecialDAO.isActivo(idPeriodoAcademico, idCampus);
    }
    
    public TipoPagoEspecial getTipoPagosEspecialesByAlumno(AlumnoEstudio alumnoEstudio) {
        List<PensionAlumno> apLista = this.getPensionesListByAlumno(alumnoEstudio);
        return pagoEspecialDAO.getTipoPagosEspecialesByAlumno(apLista);
    }

    public boolean isProcesadoPagosEspecialesByAlumno(AlumnoEstudio alumnoEstudio) {
        List<PensionAlumno> apLista = this.getPensionesListByAlumno(alumnoEstudio);
        return pagoEspecialDAO.isProcesadoPagosEspecialesByAlumno(alumnoEstudio, apLista, true);
    }

    public List<TipoPagoEspecial> getTipoPagoEspecialList() {
        return pagoEspecialDAO.getTipoPagoEspecialList();
    }

    public TipoPagoEspecial getTipoPagoEspecial(int id) {
        return pagoEspecialDAO.getTipoPagoEspecial(id);
    }

    public List<PensionAlumno> getPensionesListByAlumno(AlumnoEstudio alumnoEstudio) {
        return pensionesDAO.getPensionesListByAlumno(alumnoEstudio);
    }

    public List<FechaCuotaPagoEspecial> getFechaCuotaPagoEspecial(int idPeriodo, int idCampus, int idTipo) {
        return pagoEspecialDAO.getFechaCuotaPagoEspecial(idPeriodo, idCampus, idTipo);
    }

public void savePagoEspecial(AlumnoEstudio alumnoEstudio, 
            List<FechaCuotaPagoEspecial> pagoEspecialList,
            HttpServletResponse response) {
        Alumno alumno = usuarioService.getInfoUsuario();
        alumnoEstudio.setAlumno(alumno);
        pagoEspecialDAO.saveFechaCuotaPagoEspecial(alumnoEstudio.getAlumno().getId(),
                pagoEspecialList.get(0).getPagoEspecial().getId(), pagoEspecialList.get(0).getTipoPagoEspecial().getId());
        boolean isEspecial = false;
        boolean inQuincena = false;
        if (pagoEspecialList.get(0).getTipoPagoEspecial().getId() == TipoPagoEspecial.ESPECIAL_QUINCENA.getId()) {
            isEspecial = true;
            inQuincena = true;
        } else if (pagoEspecialList.get(0).getTipoPagoEspecial().getId() == TipoPagoEspecial.ESPECIAL_FIN_DE_MES.getId()) {
            isEspecial = true;
        }
        List<PensionAlumno> apLista = this.getPensionesListByAlumno(alumnoEstudio);
        pagoEspecialDAO.saveEnPensiones(alumnoEstudio, apLista, isEspecial, inQuincena);

        /*
         * envío email
         */
        List<Email> email = mensajeriaDAO.getEmailValidSend(alumnoEstudio.getAlumno().getId());
        String To = "";
        if (alumnoEstudio.getCampus().getId() == Campus.Piura.getId()) {
            To = AppConstants.MAIL_PENSIONES_PIURA;
        } else if (alumnoEstudio.getCampus().getId() == Campus.Lima.getId()) {
            To = AppConstants.MAIL_PENSIONES_LIMA;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String fechasPago = "";

        for (FechaCuotaPagoEspecial fcpe : pagoEspecialList) {
            fechasPago += "\n" + sdf.format(fcpe.getFecha());
        }

        String body = String.format(AppConstants.MAIL_BODY_PAGO_ESPECIAL,
                alumnoEstudio.getAlumno().getNombres(),
                alumnoEstudio.getAlumno().getApellidoPaterno(),
                alumnoEstudio.getAlumno().getApellidoMaterno(),
                alumnoEstudio.getCarne(),
                utilDAO.getFechaActual(),
                pagoEspecialList.get(0).getTipoPagoEspecial().getNombre(),
                fechasPago);
        if (email != null) {
            mailService.send(email.get(0).getEmail(), To, AppConstants.MAIL_SUBJECT_PAGO_ESPECIAL, body);
        } else {
            mailService.send(To, To, AppConstants.MAIL_SUBJECT_PAGO_ESPECIAL, body);
        }
        alumnoEstudio.setAlumno(usuarioService.getInfoUsuario());
       // UtilReplacePlantilla utils = new UtilReplacePlantilla();
       // utils.generaDocPagoEspecial(alumnoEstudio, pagoEspecialList, response);
    }
    
    public boolean isAlumnoMoroso(AlumnoEstudio alumnoEstudio){
        return webServiceArgusDAO.isMoroso(alumnoEstudio.getCampus(),usuarioService.getInfoUsuario().getCarne(), alumnoEstudio.getEdicionestudio().getId());
    }
    
    public boolean tieneDeudaMatricula(Campus campus, String carne, int programaId){
        return webServiceArgusDAO.tieneDeudaMatricula(campus, carne, programaId);
    }
    
    public Map<String,Object> obtenerCronogramaPagos(AlumnoEstudio alumnoEstudio){
        return webServiceArgusDAO.infoAlumnoCronogramaPagos(alumnoEstudio.getCampus(),usuarioService.getInfoUsuario().getCarne()
                            ,alumnoEstudio.getEdicionestudio().getId(),alumnoEstudio.getPeriodoAcademicoVigente().getId());
    }
    public Map<String,Object> obtenerCuotaPagos(int cpgp,int idcampus){
        return webServiceArgusDAO.infoAlumnoCuotaPagos(cpgp,idcampus);
    }
    public Map<String,Object> obtenerRecibodePagos(int nrecibo,int idcampus){
        return webServiceArgusDAO.infoAlumnoRecibodePagos(nrecibo,idcampus);
    }
}
