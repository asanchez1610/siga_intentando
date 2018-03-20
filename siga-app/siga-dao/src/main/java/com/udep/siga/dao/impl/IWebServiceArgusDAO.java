/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.udep.siga.dao.impl;

import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.dao.UtilDAO;
import com.udep.siga.dao.WebServiceArgusDAO;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.URLWebService;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Repository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Gary Ayala
 */
@Repository("webServiceArgusDAO")
public class IWebServiceArgusDAO implements WebServiceArgusDAO{
    private final String ERROR_CONEXION = "ERROR: Problemas de conexión al consumir Web Service. ";
    private final String ERROR_GENERICO = "ERROR: Problemas al consumir Web Service. ";
    private static Logger log = Logger.getLogger(IWebServiceArgusDAO.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UtilDAO utilDAO;

    @Override
    public Map<String, Object> infoAlumnoEstudio(Campus campus, String carne, int edicionEstudioId) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.infoAlumnoEstudio.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("carne", carne);
        params.add("programaId", String.valueOf(edicionEstudioId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return result;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
        result = new HashMap<String, Object>();
        result.put("moroso", false);
        result.put("periodoIngreso", "");
        result.put("colegio", "");
        return result;
    }
    
    @Override
    public String infoAlumnoTipoBeca(Campus campus, String carne
                                , int edicionEstudioId
                                , int periodoAcademicoId) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoTipoBeca.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("carne", carne);
        params.add("programaId", String.valueOf(edicionEstudioId));
        params.add("semestreId", String.valueOf(utilDAO.getSemestreSigaToArgus(campus.getId(), periodoAcademicoId)));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            Map<String, Object> result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return result.get("tipoBeca").toString();
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
        return "";
    }
    
    @Override
    public Map<String, Object> infoAlumnoHistorialIdiomas(Campus campus, String carne) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.historialAcademicoIdiomas.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("carne", carne);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return result;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
        result = new HashMap<String, Object>();
        result.put("historial", new ArrayList<Map<String, Object>>(0));
        return result;
    }
    
    @Override
    public Map<String, Object> infoAlumnoCronogramaPagos(Campus campus, String carne, int edicionEstudioId,
        int periodoAcademicoId) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoCronogramaPagos.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("carne", carne);
        params.add("semestreId", String.valueOf(utilDAO.getSemestreSigaToArgus(campus.getId(), periodoAcademicoId)));
        params.add("programaId", String.valueOf(edicionEstudioId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return result;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
        result = new HashMap<String, Object>();
        result.put("pensiones", new ArrayList<Map<String, Object>>(0));
        result.put("idiomas", new ArrayList<Map<String, Object>>(0));
        result.put("otros", new ArrayList<Map<String, Object>>(0));
        return result;
    }
    
     @Override
    public Map<String, Object> infoAlumnoRecibodePagos(int nrecibo,int idcampus) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoRecibodePagos.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        
        params.add("nrecibo",String.valueOf(nrecibo)); 
        params.add("campusId",String.valueOf(idcampus));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return result;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
        result = new HashMap<String, Object>();
        result.put("detalle", new ArrayList<Map<String, Object>>(0));
       
        return result;
    }
    
    @Override
    public Map<String, Object> infoAlumnoCuotaPagos(int cpgp,int idcampus) {
       String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoCuotasdePagos.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cpgp",String.valueOf(cpgp)); 
        params.add("campusId",String.valueOf(idcampus));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return result;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
        result = new HashMap<String, Object>();
        result.put("detalle", new ArrayList<Map<String, Object>>(0));
       
        return result;
    }
    
    @Override
    public Map<String, Object> infoAlumnoReceptorCorrespondencia(Campus campus, String carne) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoResponsableEconomico.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("carne", carne);
     
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return result;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
            
        result = new HashMap<String, Object>();
        result.put("nombres", "");
        result.put("apellidos", "");
        result.put("direccion", "");
        result.put("ciudad", "");
        return result;
    }
    
    @Override
    public Map<String, Object> infoAlumnoFamiliaMiembroById(Campus campus, String carne, int tipoMiembroId) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoInfoMiembro.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("carne", carne);
        params.add("tipoMiembroId", String.valueOf(tipoMiembroId));
     
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return result;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
            ex.printStackTrace();
        }
        
        result = new HashMap<String, Object>();
        result.put("id", 0);
        result.put("apellidos", "");
        result.put("nombres", "");
        result.put("email", "");
        result.put("direccion", "");
        result.put("telefonofijo", "");
        result.put("telefonocelular", "");
        return result;
    }
    
    @Override
    public boolean infoAlumnoFamiliaMiembroUpdateById(Campus campus, int id, 
        String email, String direccion, String telfijo, String telcelular) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoInfoMiembroUpdate.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("id", String.valueOf(id));
        params.add("email", email);
        params.add("direccion", direccion);
        params.add("telfijo", telfijo);
        params.add("telcelular", telcelular);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            boolean isUpdate = Boolean.parseBoolean(result.get("rpta").toString());
            return isUpdate;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }        
        return false;
    }

    public boolean isMoroso(Campus campus, String carne, int edicionEstudioId) {
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoIsMoroso.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("carne", carne);
        params.add("programaId", String.valueOf(edicionEstudioId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        Map<String, Object> result;
        try {
            result = (Map<String, Object>) restTemplate.postForObject(URL,request, Map.class);

            System.out.println("-- " + result);
            
            return Boolean.parseBoolean(result.get("moroso").toString());
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean tieneDeudaMatricula(Campus campus, String carne, int programaId){
        String URL = AppConstants.SERVIDOR_WEB_SERVICE_URL + URLWebService.alumnoDebeMatricula.getUrl();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("campusId", String.valueOf(campus.getId()));
        params.add("carne", carne);
        params.add("programaId", String.valueOf(programaId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            ResponseEntity<Boolean> result = restTemplate.postForEntity(URL, request, Boolean.class);
            if( result.getBody() != null )
                return result.getBody();
            return false;
        } catch (RestClientException ex) {
            System.out.println(ERROR_CONEXION + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ERROR_GENERICO + ex.getMessage());
        }
        return false;
    }
}
