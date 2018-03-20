package com.udep.siga.dao;

import com.udep.siga.bean.DocumentoInfoCateg;
import com.udep.siga.bean.DocumentoInfo;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public interface DocumentoInfoDAO {
    public List<DocumentoInfoCateg> getCategoriaList();
    public List<DocumentoInfo> getDocumentoInfoList(int idCategoria);
}
