package com.udep.siga.bean.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.udep.siga.bean.enumeration.TipoEstudio;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wilfredo Atochea
 */
@Component
public class JsonTipoEstudioSerializer extends JsonSerializer<TipoEstudio>{
    @Override
    public void serialize(TipoEstudio t, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(t.getId());
        gen.writeFieldName("nombre");
        gen.writeString(t.getNombre());
        gen.writeEndObject();
    }
}
