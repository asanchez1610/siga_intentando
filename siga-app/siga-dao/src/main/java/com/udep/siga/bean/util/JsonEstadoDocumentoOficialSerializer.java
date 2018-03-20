package com.udep.siga.bean.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.udep.siga.bean.enumeration.EstadoDocumentoOficial;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wilfredo Atoche
 */
@Component
public class JsonEstadoDocumentoOficialSerializer extends JsonSerializer<EstadoDocumentoOficial> {

    @Override
    public void serialize(EstadoDocumentoOficial t, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(t.getId());
        gen.writeFieldName("nombre");
        gen.writeString(t.getNombre());
        gen.writeFieldName("pasoSiguiente");
        gen.writeString(t.getPasoSiguiente());        
        gen.writeEndObject();
    }
}
