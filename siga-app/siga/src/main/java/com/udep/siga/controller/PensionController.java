package com.udep.siga.controller;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.FechaCuotaPagoEspecial;
import com.udep.siga.bean.enumeration.TipoPagoEspecial;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.PensionService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.UtilReplacePlantilla;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrador
 */
@Controller
@RequestMapping("/json/pensiones/*")
public class PensionController {

    @Autowired
    private CommonsService commonsService;
    @Autowired
    private PensionService pensionService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AlumnoEstudioService alumnoEstudioService;

    @RequestMapping(value = "cronogramas.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCronogramas(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Pensiones");
        Alumno alumno = usuarioService.getInfoUsuario();
        data.put("nombre", String.format("%s %s %s", alumno.getNombres(), alumno.getApellidoPaterno(),
                alumno.getApellidoMaterno()));
        data.put("carne", alumnoEstudio.getCarne());
       
        data.put("periodo", pensionService.getCronogramaPagosPensionesList(alumnoEstudio));
//        data.put("moroso", alumnoEstudio.isMoroso());
        data.put("pagoEspecialActivo", false);
//        data.put("moroso", pensionService.isAlumnoMoroso(alumnoEstudio));
        data.put("moroso", usuarioService.isAlumnoMoroso(alumnoEstudio));
        
        Map<String,Object> cronograma = pensionService.obtenerCronogramaPagos(alumnoEstudio);
        data.put("pregrado", cronograma.get("pensiones"));
        data.put("idiomas", cronograma.get("idiomas"));
        data.put("otras_deudas", cronograma.get("otros"));
        
        TipoPagoEspecial tipoPago = pensionService.getTipoPagosEspecialesByAlumno(alumnoEstudio);
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null && alumnoEstudio.getCampus() != null
                && alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio() != null ){ 
            String isPregrado = alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio().getNombre();        
            boolean activoPagoEspecial = pensionService.isActivoPagoEspecial
                    (alumnoEstudio.getPeriodoAcademicoVigente().getId(), alumnoEstudio.getCampus().getId());
            boolean cachimbo = alumnoEstudioService.isCachimbo(alumnoEstudio);
             data.put("cachimbo",cachimbo);
            if(isPregrado.equals("Pregrado") && tipoPago != null && activoPagoEspecial ){
                data.put("pagoEspecialActivo", true);
            }
            data.put("tipoestudio", isPregrado);
        }
        
        return data;
    }
    
     @RequestMapping(value = "pagos.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object>
            Verpagoscuota(@RequestParam(value = "cpgp") String cpgp0,@RequestParam("idEdicionestudio") String idEdicionestudioRandom
             ,@RequestParam(value = "fechav") String fechav,@RequestParam(value = "cuota") String cuota)
             {
           Map<String, Object> data = new HashMap<String, Object>();
            int cpgp = Integer.parseInt(cpgp0);
          try {
            Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
            AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
           // List<AlumnoEstudioPeriodoAcademico> periodos = alumnoEstudioService.getEstudiosHistorialByAlumno(alumnoEstudio);
            //alumnoEstudio.setAlumno(alumnoEstudioService.getById(alumnoEstudio.getAlumno().getId()));
           Map<String,Object> pagosdecuota = pensionService.obtenerCuotaPagos(cpgp,alumnoEstudio.getCampus().getId());
          // System.out.println("cpgp : " +cpgp+" idcampus :"+alumnoEstudio.getCampus().getId()+" cuota: "+cuota+" fechav:"+fechav);
           data.put("pregrado", pagosdecuota.get("detalle")); 
           data.put("ncuota",cuota );
           data.put("fechav",fechav );
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return data;
    }
    
      @RequestMapping(value = "descargaToPDF/{id}/{nrecibo}", method = RequestMethod.GET)
    public @ResponseBody String
            DescargaHistorialToPDF(@PathVariable(value = "id") String idEdicionestudioRandom,
            @PathVariable(value = "nrecibo") String nrecibo,
            HttpServletResponse response) {
          int nrecibo1 = Integer.parseInt(nrecibo);
          
        try {
            Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
            AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
            Map<String,Object> pagosdecuota = pensionService.obtenerRecibodePagos(nrecibo1,alumnoEstudio.getCampus().getId());
             ArrayList<Map<String, Object>> detalles =(ArrayList<Map<String, Object>>)pagosdecuota.get("detalle");
             Map<String, Object> map = detalles.get(0);
            // System.out.println("valores :"+map.toString());
           
            UtilReplacePlantilla utils = new UtilReplacePlantilla();
            utils.generaReportePensionesToPdf(map, response,alumnoEstudio);  
        } catch (Exception e) {
            System.out.println("Error al crear recibo electronico: " + e.getMessage());
        }
        return "ok";
    }
    
    @RequestMapping(value = "estadoMoroso.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getEstadoMoroso(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        
        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("moroso", alumnoEstudio.isMoroso());
//        data.put("moroso", pensionService.isAlumnoMoroso(alumnoEstudio));
        data.put("moroso", usuarioService.isAlumnoMoroso(alumnoEstudio));
        return data;
    }
    
    @RequestMapping(value = "pagosEspeciales.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getPagosEspeciales(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Pensiones");
        Alumno alumno = usuarioService.getInfoUsuario();
        data.put("nombre", String.format("%s %s %s", alumno.getNombres(), alumno.getApellidoPaterno(),
                alumno.getApellidoMaterno()));
        data.put("carne", alumnoEstudio.getCarne());
        data.put("ciclo", alumnoEstudio.getCiclo());
        data.put("campus", alumnoEstudio.getCampus().getId());
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null){
            data.put("periodo", alumnoEstudio.getPeriodoAcademicoVigente());
        }
        TipoPagoEspecial tipoPagoEspecial = pensionService.getTipoPagosEspecialesByAlumno
                (alumnoEstudio);
        data.put("tipoPago", tipoPagoEspecial);
         boolean cachimbo = alumnoEstudioService.isCachimbo(alumnoEstudio);
             data.put("cachimbo",cachimbo);
        data.put("procesado", pensionService.isProcesadoPagosEspecialesByAlumno(alumnoEstudio));
        data.put("listTipoPago", pensionService.getTipoPagoEspecialList());
        data.put("fechaPagoListDefault", pensionService.getFechaCuotaPagoEspecial
                (alumnoEstudio.getPeriodoAcademicoVigente().getId(), alumnoEstudio.getCampus().getId(), tipoPagoEspecial.getId()));
        return data;
    }
    
    @RequestMapping(value = "fechaPagosEspeciales.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> fechaPagosEspeciales(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom,
            @RequestParam(value = "idTipo", required = true) int idTipo) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Pensiones");        
        data.put("fechaPagoList", pensionService.getFechaCuotaPagoEspecial
                (alumnoEstudio.getPeriodoAcademicoVigente().getId(), alumnoEstudio.getCampus().getId(), idTipo));
        
        return data;
    }
    
    @RequestMapping(value = "verifySavePagoEspecial.json", method = RequestMethod.POST)
    public  @ResponseBody Map<String, Object> verifySavePagoEspecial(@RequestParam("idedicionestudio") String idEdicionestudioRandom,
            @RequestParam("tipo") Integer idTipo, HttpServletResponse response) {
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        List<FechaCuotaPagoEspecial> fechas = pensionService.getFechaCuotaPagoEspecial(alumnoEstudio.getPeriodoAcademicoVigente().getId(), alumnoEstudio.getCampus().getId(), idTipo);

        pensionService.savePagoEspecial(alumnoEstudio, fechas, response);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rpta", "ok");
        return data;
    }
}
