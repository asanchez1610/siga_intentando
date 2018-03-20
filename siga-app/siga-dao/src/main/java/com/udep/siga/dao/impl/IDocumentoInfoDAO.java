package com.udep.siga.dao.impl;

import com.udep.siga.bean.DocumentoInfo;
import com.udep.siga.bean.DocumentoInfoCateg;
import com.udep.siga.dao.DocumentoInfoDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("documentoInfoDAO")
public class IDocumentoInfoDAO extends CustomizeJdbcDaoSupport implements DocumentoInfoDAO{

    public List<DocumentoInfoCateg> getCategoriaList() {
        String sql = "SELECT IDCATEGORIADOCUMENTOS, NOMBRE "
                + "FROM CATEGORIADOCUMENTOS WHERE VERSOLOASESORAMIENTO = 0 "
                + "ORDER BY ORDEN;";
        List<DocumentoInfoCateg> documentoInfoCategList = this.getJdbcTemplate()
                .query(sql, UtilRowMapper.getDocumentoInfoCategMapper());
        return documentoInfoCategList;
    }

    public List<DocumentoInfo> getDocumentoInfoList(int idCategoria) {
        String sql = "SELECT NOMBRE, DESCRIPCION, URLDOCUMENTO "
                + "FROM REPOSITORIODOCUMENTOS RD "
                + "WHERE RD.PUBLICADO = 1 AND RD.VERSOLOALUMNOS = 1 AND IDCATEGORIADOCUMENTOS = ?";
        List<DocumentoInfo> documentoInfoList = this.getJdbcTemplate()
                .query(sql, new Object[]{idCategoria}, UtilRowMapper.getDocumentoInfoMapper());
        return documentoInfoList;
    }

}
