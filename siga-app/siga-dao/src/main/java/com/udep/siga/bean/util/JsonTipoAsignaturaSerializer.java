package com.udep.siga.bean.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.udep.siga.bean.enumeration.TipoAsignatura;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrador
 */
@Component
public class JsonTipoAsignaturaSerializer extends JsonSerializer<TipoAsignatura>{
    @Override
    public void serialize(TipoAsignatura t, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(t.getId());
        gen.writeFieldName("descripcion");
        gen.writeString(t.getDescripcion());
        gen.writeFieldName("sigla");
        gen.writeString(t.getSigla());
        gen.writeFieldName("creditoDescripcion");
        gen.writeString(t.getCreditoDescripcion());
        
        gen.writeEndObject();
    }
}
