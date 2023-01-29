package com.test.haud.spamfiltergatewayservice.config;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class AvroMessageConverter implements MessageConverter {

    private Schema schema; // AvroSchema.getInstance().getSchema();

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        DatumWriter<GenericRecord> writer = new SpecificDatumWriter<>(schema);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
        try {
            GenericRecord record = (GenericRecord) object;
            writer.write(record, encoder);
            encoder.flush();
            outputStream.close();
            messageProperties.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE);
            messageProperties.setContentEncoding("avro");
            return new Message(outputStream.toByteArray(), messageProperties);
        } catch (IOException e) {
            throw new MessageConversionException("Error serializing object to Avro", e);
        }
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        byte[] bytes = message.getBody();
        SpecificDatumReader<GenericRecord> reader = new SpecificDatumReader<>(schema);
        try {
            return reader.read(null, org.apache.avro.io.DecoderFactory.get().binaryDecoder(bytes, null));
        } catch (IOException e) {
            throw new MessageConversionException("Error deserializing Avro message", e);
        }
    }
}
