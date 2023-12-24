package de.fhws.fiw.fds.sutton.server.api.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.jakarta.rs.xml.JacksonXmlBindXMLProvider;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import java.time.format.DateTimeFormatter;

public class JacksonConfig {

    private JavaTimeModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        return javaTimeModule;
    }

    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(javaTimeModule())
                .registerModule(new JakartaXmlBindAnnotationModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .findAndRegisterModules();
    }

    public JacksonJaxbJsonProvider jsonProvider() {
        return new JacksonJaxbJsonProvider(objectMapper(), JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
    }

    public JacksonXmlBindXMLProvider xmlProvider() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(javaTimeModule());
        xmlMapper.registerModule(new JakartaXmlBindAnnotationModule());
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.findAndRegisterModules();
        return new JacksonXmlBindXMLProvider(xmlMapper, JacksonXmlBindXMLProvider.DEFAULT_ANNOTATIONS);
    }
}
