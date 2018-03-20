package com.udep.siga.bean.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.udep.siga.bean.enumeration.EstadoAlumno;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wilfredo Atoche
 */
@Component
public class JsonEstadoAlumnoSerializer extends JsonSerializer<EstadoAlumno> {

    @Override
    public void serialize(EstadoAlumno t, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(t.getId());
        gen.writeFieldName("nombre");
        gen.writeString(t.getNombre());
        gen.writeEndObject();
    }
    
}
