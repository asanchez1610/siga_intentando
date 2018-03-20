package com.udep.siga.bean.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.udep.siga.bean.enumeration.EstadoEvaluacion;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wilfredo Atoche
 */
@Component
public class JsonEstadoEvaluacionSerializer extends JsonSerializer<EstadoEvaluacion> {

    @Override
    public void serialize(EstadoEvaluacion t, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(t.getId());
        gen.writeFieldName("nombre");
        gen.writeString(t.getNombre());
        gen.writeEndObject();
    }
    
}
