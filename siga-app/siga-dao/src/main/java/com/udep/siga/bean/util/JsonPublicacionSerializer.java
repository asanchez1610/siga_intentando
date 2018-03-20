package com.udep.siga.bean.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.udep.siga.bean.enumeration.RestriccionPublicacion;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrador
 */
@Component
public class JsonPublicacionSerializer extends JsonSerializer<RestriccionPublicacion>{
    @Override
    public void serialize(RestriccionPublicacion r, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(r.getId());
        gen.writeFieldName("nombre");
        gen.writeString(r.getNombre());
        gen.writeEndObject();
    }
}
