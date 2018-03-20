package com.udep.siga.bean.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.udep.siga.bean.enumeration.Configuracion;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrador
 */
@Component
public class JsonConfiguracionSerializer extends JsonSerializer<Configuracion>{
    @Override
    public void serialize(Configuracion c, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(c.getId());
        gen.writeEndObject();
    }
}
