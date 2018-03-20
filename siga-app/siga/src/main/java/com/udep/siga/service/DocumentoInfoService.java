package com.udep.siga.service;

import com.udep.siga.bean.DocumentoInfo;
import com.udep.siga.bean.DocumentoInfoCateg;
import com.udep.siga.dao.DocumentoInfoDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("documentoInfoService")
public class DocumentoInfoService {

    @Autowired
    private DocumentoInfoDAO documentoInfoDAO;
    @Autowired
    private UsuarioService usuarioService;

    public List<DocumentoInfoCateg> getCategoriaList() {
        List<DocumentoInfoCateg> list = documentoInfoDAO.getCategoriaList();

        for (DocumentoInfoCateg documentoInfoCateg : list) {
            List<DocumentoInfo> documentoList = documentoInfoDAO.getDocumentoInfoList(documentoInfoCateg.getId());
            
            for (DocumentoInfo documentoInfo : documentoList) {
                documentoInfo.setUrl(usuarioService.addRefIndirecta(documentoInfo.getUrl()));
            }
            
            documentoInfoCateg.setDocumentoInfoList(documentoList);
        }
        
        return list;
    }
}
