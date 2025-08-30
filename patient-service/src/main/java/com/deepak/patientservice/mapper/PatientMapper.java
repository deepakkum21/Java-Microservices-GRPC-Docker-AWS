package com.deepak.patientservice.mapper;

import com.deepak.patientservice.dto.PatientRequestDto;
import com.deepak.patientservice.model.Patient;
import com.deepak.patientservice.dto.PatientResponseDto;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDto toDTO(Patient patient) {
        PatientResponseDto patientDTO = new PatientResponseDto();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }

    public static Patient toModel(PatientRequestDto patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;
    }
}
