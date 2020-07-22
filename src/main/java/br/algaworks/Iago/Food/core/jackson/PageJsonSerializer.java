package br.algaworks.Iago.Food.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    // Serializador JSON para os retornos do tipo Page
    @Override
    public void serialize(Page<?> page, JsonGenerator gen,
                          SerializerProvider serializerProvider) throws IOException {

        gen.writeStartObject();

        gen.writeObjectField("content", page.getContent());
        gen.writeNumberField("size", page.getSize());
        gen.writeNumberField("totalPages", page.getTotalPages());
        gen.writeNumberField("totalElements", page.getTotalElements());
        gen.writeNumberField("number", page.getNumber());

        gen.writeEndObject();
    }

}
