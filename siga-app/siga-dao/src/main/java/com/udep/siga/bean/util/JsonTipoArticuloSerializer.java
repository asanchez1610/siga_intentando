package com.udep.siga.bean.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.udep.siga.bean.enumeration.TipoArticulo;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrador
 */
@Component
public class JsonTipoArticuloSerializer extends JsonSerializer<TipoArticulo>{
    @Override
    public void serialize(TipoArticulo t, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(t.getId());
        gen.writeFieldName("nombre");
        gen.writeString(t.getNombre());
        gen.writeEndObject();
    }
}
