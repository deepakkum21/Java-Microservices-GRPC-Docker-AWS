package com.deepak.patientservice.kafka;

import com.deepak.patientservice.exception.KafkaPatientProduceException;
import com.deepak.patientservice.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Value( "${kafka.topic:patient}")
    private String TOPIC;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent .newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try {
            kafkaTemplate.send(TOPIC, event.toByteArray());
            log.info("Event sent to Kafka topic '{}' '{}'", TOPIC, event);

        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", event);
            throw new KafkaPatientProduceException("Error sending PatientCreated event");
        }
    }
}
