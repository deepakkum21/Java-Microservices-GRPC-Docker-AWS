package com.deepak.patientservice.exception;

public class KafkaPatientProduceException extends RuntimeException{
    public KafkaPatientProduceException(String message){
        super(message);
    }
}
