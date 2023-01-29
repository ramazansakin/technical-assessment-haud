//package com.test.haud.spamfiltergatewayservice.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.avro.Schema;
//
//import java.io.IOException;
//
//@Slf4j
//public class AvroSchema {
//
//    private static AvroSchema instance;
//    private Schema schema;
//
//    private AvroSchema() {
//        // Read the schema from a file or hard code it
//        try {
//            schema = new Schema.Parser().parse(AvroSchema.class.getResourceAsStream("avro/sms.avsc"));
//        } catch (IOException e) {
//            log.error("Error occurred while parsing schema", e);
//        }
//    }
//
//    public static AvroSchema getInstance() {
//        if (instance == null) {
//            instance = new AvroSchema();
//        }
//        return instance;
//    }
//
//    public Schema getSchema() {
//        return schema;
//    }
//}
